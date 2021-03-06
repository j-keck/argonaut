package argonaut.internal

import argonaut._
import scala.collection.immutable.{ SortedMap, MapLike }
import scalaz._, Scalaz._

object Macros extends MacrosCompat {
  def materializeCodecImpl[T: c.WeakTypeTag](c: Context): c.Expr[CodecJson[T]] = {
    import c.universe._
    val tpe = weakTypeOf[T]
    val encode = materializeEncodeImpl[T](c)
    val decode = materializeDecodeImpl[T](c)
    c.Expr[CodecJson[T]](q"""
    CodecJson.derived[$tpe]($encode, $decode)
    """)
  }

  def materializeEncodeImpl[T: c.WeakTypeTag](c: Context): c.Expr[EncodeJson[T]] = {
    import c.universe._
    val tpe = weakTypeOf[T]

    val primaryConstructor = getDeclarations(c)(tpe).collectFirst{
      case m: MethodSymbol if m.isPrimaryConstructor => m
    }
    primaryConstructor match {
      case Some(constructor) => {
        val fieldNames: List[c.universe.Name] = getParameterLists(c)(constructor).flatten.map{field => 
          field.name
        }
        val decodedNames: List[String] = fieldNames.map(_.decodedName.toString)
        val fieldTypes: List[c.universe.Type] = getParameterLists(c)(constructor).flatten.map{field =>
          getDeclaration(c)(tpe, field.name).typeSignature
        }
        val fieldCount = fieldNames.size
        val invocations = decodedNames.map{fieldName => 
          val termName = createTermName(c)(fieldName)
          q"toEncode.$termName"
        }
        val methodName = createTermName(c)("jencode" + (fieldCount.toString) + "L")
        c.Expr[EncodeJson[T]]{q"""
          EncodeJson.$methodName[$tpe, ..$fieldTypes](toEncode => (..$invocations))(..$decodedNames)
        """}
      }
      case None => c.abort(c.enclosingPosition, "Could not identify primary constructor for " + tpe)
    }
  }

  def materializeDecodeImpl[T: c.WeakTypeTag](c: Context): c.Expr[DecodeJson[T]] = {
    import c.universe._
    val tpe = weakTypeOf[T]

    val primaryConstructor = getDeclarations(c)(tpe).collectFirst{
      case m: MethodSymbol if m.isPrimaryConstructor => m
    }
    primaryConstructor match {
      case Some(constructor) => {
        val fieldNames: List[c.universe.Name] = getParameterLists(c)(constructor).flatten.map{field => 
          field.name
        }
        val decodedNames: List[String] = fieldNames.map(_.decodedName.toString)
        val fieldTypes: List[c.universe.Type] = getParameterLists(c)(constructor).flatten.map{field =>
          getDeclaration(c)(tpe, field.name).typeSignature
        }
        val fieldCount = fieldNames.size
        val functionParameters = decodedNames.zip(fieldTypes).map{case (fieldName, fieldType) =>
          val termName = createTermName(c)(fieldName)
          q"$termName: $fieldType"
        }
        val parameters = decodedNames.map{fieldName =>
          val termName = createTermName(c)(fieldName)
          q"$termName"
        }
        val methodName = createTermName(c)("jdecode" + (fieldCount.toString) + "L")
        c.Expr[DecodeJson[T]]{q"""
          DecodeJson.$methodName[..$fieldTypes, $tpe]((..$functionParameters) => new $tpe(..$parameters))(..$decodedNames)
        """}
      }
      case None => c.abort(c.enclosingPosition, "Could not identify primary constructor for " + tpe)
    }
  }
}