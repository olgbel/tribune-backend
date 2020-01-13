package ru.netology.route

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.features.ParameterConversionException
import io.ktor.http.content.files
import io.ktor.http.content.static
import io.ktor.request.receive
import io.ktor.request.receiveMultipart
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.util.KtorExperimentalAPI
import ru.netology.dto.AuthenticationRequestDto
import ru.netology.dto.PostRequestDto
import ru.netology.dto.RegistrationRequestDto
import ru.netology.dto.UserResponseDto
import ru.netology.model.UserModel
import ru.netology.service.FileService
import ru.netology.service.PostService
import ru.netology.service.UserService

@KtorExperimentalAPI
class RoutingV1(
    private val staticPath: String,
    private val postService: PostService,
    private val fileService: FileService,
    private val userService: UserService
) {
    fun setup(configuration: Routing) {
        with(configuration) {
            route("/api/v1/") {
                static("/static") {
                    files(staticPath)
                }

                route("") {
                    post("/registration") {
                        val input = call.receive<RegistrationRequestDto>()
                        val response = userService.registration(input)
                        call.respond(response)
                    }

                    post("/authentication") {
                        val input = call.receive<AuthenticationRequestDto>()
                        val response = userService.authenticate(input)
                        call.respond(response)
                    }
                }

                authenticate {
                    route("/me") {
                        get {
                            val me = call.authentication.principal<UserModel>()
                            call.respond(UserResponseDto.fromModel(me!!))
                        }
                    }

                    route("/media") {
                        post {
                            val multipart = call.receiveMultipart()
                            val response = fileService.save(multipart)
                            call.respond(response)
                        }
                    }

                    route("/posts") {
                        post {
                            val input = call.receive<PostRequestDto>()
                            val me = call.authentication.principal<UserModel>()
//                            val response = me?.id?.let { it1 -> postService.save(input, it1, -1) }
//                            call.respond(response!!)
                        }

                        get("/recent") {
                            val response = postService.getRecentPosts()
                            call.respond(response)
                        }

                        get("/after/{id}") {
                            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Long"
                            )
                            val response = postService.getPostsAfter(id)
                            call.respond(response)
                        }

                        get("/before/{id}") {
                            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Long"
                            )
                            val response = postService.getPostsBefore(id)
                            call.respond(response)
                        }

                        post("/like/{id}") {
                            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Long"
                            )
                            val me = call.authentication.principal<UserModel>()
                            val response = me?.id?.let { it1 -> postService.likeById(id, it1) }
                            call.respond(response!!)
                        }
                        post("/dislike/{id}") {
                            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Long"
                            )
                            val me = call.authentication.principal<UserModel>()
                            val response = me?.id?.let { it1 -> postService.dislikeById(id, it1) }
                            call.respond(response!!)
                        }
                    }
                }
            }
        }
    }
}