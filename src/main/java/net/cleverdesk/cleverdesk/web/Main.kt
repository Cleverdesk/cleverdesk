package net.cleverdesk.cleverdesk.web

/**
 * Created by jkuche on 23.04.16.
 */


import spark.Spark.*
object HelloWorld {
    @JvmStatic fun main(args:Array<String>) {
        get("/", { req, res->
            Response(200, "Welcome to Cleverdesk").to_json()
        })
    }
}