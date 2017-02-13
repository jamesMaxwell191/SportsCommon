package com.marcom.sports.format

import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model.MediaTypes
import akka.http.scaladsl.server.{RejectionError, ValidationRejection}
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshaller}
import akka.util.ByteString
import play.api.libs.json._

trait PlayJsonSupport {

  private val jsonStringUnmarshaller =
    Unmarshaller.byteStringUnmarshaller
      .forContentTypes(MediaTypes.`application/json`)
      .mapWithCharset {
        case (ByteString.empty, _) => throw Unmarshaller.NoContentException
        case (data, charset)       => data.decodeString(charset.nioCharset.name)
      }

  private val jsonStringMarshaller =
    Marshaller.stringMarshaller(MediaTypes.`application/json`)

  /**
    * HTTP entity => `A`
    *
    * @param reads reader for `A`
    * @tparam A type to decode
    * @return unmarshaller for `A`
    */
  implicit def playJsonUnmarshaller[A](
                                        implicit reads: Reads[A]
                                      ): FromEntityUnmarshaller[A] = {
    def read(json: JsValue) =
      reads
        .reads(json)
        .recoverTotal(
          error =>
            throw new RejectionError(
              ValidationRejection(JsError.toJson(error).toString,
                Some(PlayJsonError(error))))
        )
    jsonStringUnmarshaller.map(data => read(Json.parse(data)))
  }

  /**
    * `A` => HTTP entity
    *
    * @param writes writer for `A`
    * @param printer pretty printer function
    * @tparam A type to encode
    * @return marshaller for any `A` value
    */
  implicit def playJsonMarshaller[A](
                                      implicit writes: Writes[A],
                                      printer: JsValue => String = Json.prettyPrint
                                    ): ToEntityMarshaller[A] =
    jsonStringMarshaller.compose(printer).compose(writes.writes)
}

case class PlayJsonError(error: JsError) extends RuntimeException {
  override def getMessage: String = JsError.toJson(error).toString()
}
