/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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