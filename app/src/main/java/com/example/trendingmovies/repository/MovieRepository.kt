package com.example.trendingmovies.repository

import com.example.trendingmovies.data.api.MovieApiInterface
import com.example.trendingmovies.data.model.movieData.MovieResponse
import com.example.trendingmovies.data.model.movieDetails.MovieDetails
import com.example.trendingmovies.util.ApiConstants
import com.example.trendingmovies.util.Result
import com.example.trendingmovies.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


class MovieRepository(private val movieApiInterface: MovieApiInterface) {
    suspend fun getMoviesList( s: String, pageNo:Int,apiKey:String): Result<MovieResponse>{
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApiInterface.getAllMovies(s,pageNo,apiKey)
                if (response.isSuccessful) {
                    Result(Status.SUCCESS, response.body(), response.body()?.totalResults.toString())
                } else {
                    var error:String
                    when(response.code()){
                        404->error="404  Not found web api"
                        401->error="401  Unauthorized Error"
                        403 -> error="403 Forbidden"
                        500 -> error="500 InternalServerError"
                        502 -> error="502 BadGateWay"
                        301 -> error="301 ResourceRemoved"
                        302 ->error="302 RemovedResourceFound"
                        else -> error=response.code().toString()+response.errorBody()
                    }
                    Result(Status.ERROR, null, error)
                }
            } catch (e: HttpException) {
                Result(Status.ERROR, null, e.toString()+"Something went wrong")
            } catch (e: IOException) {
                Result(Status.ERROR, null, e.toString()+"Please check your network connection")
            } catch (e: Exception) {
                Result(Status.ERROR, null, "something went wrong")
            }
        }

    }

    suspend fun getMovieDetails(movieId: String): Result<MovieDetails> {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApiInterface.getMovieDetails(movieId, ApiConstants.API_KEY)
                if (response.isSuccessful) {
                    Result(Status.SUCCESS, response.body(),null)
                } else {
                    var error:String
                    when(response.code()){
                        404->error="404  Not found web api"
                        401->error="401  Unauthorized Error"
                        403 -> error="403 Forbidden"
                        500 -> error="500 InternalServerError"
                        502 -> error="502 BadGateWay"
                        301 -> error="301 ResourceRemoved"
                        302 ->error="302 RemovedResourceFound"
                        else -> error=response.code().toString()+response.errorBody()
                    }
                    Result(Status.ERROR, null, error)
                }
            } catch (e: HttpException) {
                Result(Status.ERROR, null, e.toString()+"Something went wrong")
            } catch (e: IOException) {
                Result(Status.ERROR, null, e.toString()+"Please check your network connection")
            } catch (e: Exception) {
                Result(Status.ERROR, null, "something went wrong")
            }
        }

        /*eturn try {

            val response = movieApiInterface.getMovieDetails(movieId, ApiConstants.API_KEY)
            if (response.isSuccessful) {
                Result(Status.SUCCESS, response.body(), null)
            } else {
                Result(Status.ERROR, null, null)
            }
        } catch (e: Exception) {
            Result(Status.ERROR, null, null)
        }
*/

    }
}