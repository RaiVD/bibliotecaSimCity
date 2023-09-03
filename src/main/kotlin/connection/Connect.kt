package connection

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class Connect {
    fun creatConnect(): Connection {
        return try {
            val url = "jdbc:postgresql://localhost:5432/biblioteca_simCity"
            DriverManager.getConnection(url,"postgres","1234")
        }catch (e: SQLException){
            e.printStackTrace()
            throw RuntimeException("Erro ao conectar ao banco de dados")
        }
    }
}