package net.cleverdesk.cleverdesk.web
import com.google.gson.Gson
/**
 * Created by jkuche on 23.04.16.
 */
class Response(status:Int, body:Any) {
    internal var status:Int
    internal var body:Any
    init{
        this.status = status
        this.body = body
    }
    fun to_json():String {
        val gson = Gson()
        return gson.toJson(this)
    }
}