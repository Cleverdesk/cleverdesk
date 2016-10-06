import com.google.gson.Gson
import org.junit.Test

/**
 * Created by SV on 06.10.2016.
 */
class GsonTest {

    @Test
    public open fun gson() {
        val gson = GsonModel()
        gson.channel = "delta"
        gson.message = mapOf(Pair("plugin", "Gson"), Pair("page", "Test"))
        print(Gson().toJson(gson))
        val newGson = Gson().fromJson("{\"channel\":\"delta\",\"message\":{\"plugin\":\"Gson\",\"page\":\"Test\"}}", GsonModel::class.java)

    }
}

class GsonModel {
    public open var channel: String = ""
    public open var message: Any? = null;
}