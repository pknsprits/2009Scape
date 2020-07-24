package core.game.system.config

import core.game.system.SystemLogger
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.FileReader
import java.util.*
import kotlin.collections.HashMap

class XteaParser {
    companion object{
        val REGION_XTEA = HashMap<Int,IntArray>()
        val DEFAULT_REGION_KEYS = intArrayOf(14881828, -6662814, 58238456, 146761213)
        fun getRegionXTEA(regionId: Int): IntArray? { //Uses the xtea's from the sql to unlock regions
            //		System.out.println("USING SQL REGION KEYS");//Confirms we have unlocked those regions
            return  REGION_XTEA[regionId]
                    ?: //			System.out.println("USING DEFAULT REGION KEYS FOR REGION " + regionId);//Used to check for missing regions
                    return DEFAULT_REGION_KEYS //This one grabs the keys from the SQL
            //		return DEFAULT_REGION_KEYS;//This one only uses the default keys at the top,{ 14881828, -6662814, 58238456, 146761213 }. Unsure why they chose these numbers.
        }
    }
    val parser = JSONParser()
    var reader: FileReader? = null


    fun load() {
        var count = 0
        reader = FileReader("data/configs/xteas.json")
        val obj = parser.parse(reader) as JSONObject
        val configlist = obj["xteas"] as JSONArray
        for(config in configlist){
            val e = config as JSONObject
            val id = e["regionId"].toString().toInt()
            val token = StringTokenizer(e["keys"].toString(),",")
            REGION_XTEA.put(id, intArrayOf(token.nextToken().toInt(), token.nextToken().toInt(), token.nextToken().toInt(), token.nextToken().toInt()))
            count++
        }
        SystemLogger.log("[Config Parser]: Parsed $count region keys.")
    }
}