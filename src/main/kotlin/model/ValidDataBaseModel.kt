package model

import connection.Connect
import java.sql.SQLException

class ValidDataBaseModel {
    companion object {
        private val connection = Connect().creatConnect()

        // Validar ID
        fun isValidAuthorId(id: Int): Boolean {
            val sql = "SELECT COUNT(*) FROM author WHERE id=?"

            try {
                val preparedStatement = connection.prepareStatement(sql)
                preparedStatement.setInt(1, id)
                val resultSet = preparedStatement.executeQuery()
                resultSet.next()
                val count = resultSet.getInt(1)

                resultSet.close()
                preparedStatement.close()

                return count > 0
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return false
        }

        fun isValidBookId(id: Int): Boolean {
            val sql = "SELECT COUNT(*) FROM book WHERE id=?"

            try {
                val preparedStatement = connection.prepareStatement(sql)
                preparedStatement.setInt(1, id)
                val resultSet = preparedStatement.executeQuery()
                resultSet.next()
                val count = resultSet.getInt(1)

                resultSet.close()
                preparedStatement.close()

                return count > 0
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return false
        }

        fun isValidUserId(id: Int): Boolean {
            val sql = "SELECT COUNT(*) FROM users WHERE id=?"

            try {
                val preparedStatement = connection.prepareStatement(sql)
                preparedStatement.setInt(1, id)
                val resultSet = preparedStatement.executeQuery()
                resultSet.next()
                val count = resultSet.getInt(1)

                resultSet.close()
                preparedStatement.close()

                return count > 0
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return false
        }

        fun isValidLoanId(id: Int): Boolean {
            val sql = "SELECT COUNT(*) FROM loan_book WHERE id = ?"

            try {
                val preparedStatement = connection.prepareStatement(sql)
                preparedStatement.setInt(1, id)
                val resultSet = preparedStatement.executeQuery()
                resultSet.next()
                val count = resultSet.getInt(1)

                resultSet.close()
                preparedStatement.close()

                return count > 0
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return false
        }

        //Validar Indormações nulas ou vazias
        fun isValidBookInfo(nameBook: String, author: String): Boolean {
            return nameBook.isNotBlank() && author.isNotBlank()
        }

        fun isValidUserInfo(cpf: String, alias: String, email: String): Boolean {
            return cpf.isNotBlank() && alias.isNotBlank() && email.isNotBlank()
        }

        fun isValidAuthorInfo(name_author: String): Boolean {
            return name_author.isNotBlank()
        }

        // Validar entrada de email
        fun isValidEmail(email: String): Boolean {
            return email.contains("@") && email.endsWith("@gmail.com")
        }

        // Validar se ja existe emprestimo com um livro especifico
        fun isBookAlreadyLoaned(id_book: Int): Boolean {
            val sql = "SELECT COUNT(*) FROM loan_book WHERE id = ?"

            try {
                val preparedStatement = connection.prepareStatement(sql)
                preparedStatement.setInt(1, id_book)
                val resultSet = preparedStatement.executeQuery()
                resultSet.next()
                val count = resultSet.getInt(1)

                resultSet.close()
                preparedStatement.close()

                return count > 0
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return false
        }

        // Validar crdencial de Usuario e Bibliotecario
        private fun isAdmin(alias: String): Boolean {
            return alias == "José"
        }

        fun isValidLibrarianCredentials(alias: String, cpf: String): Boolean {
            if (alias.isBlank() || cpf.isBlank()) {
                throw IllegalArgumentException("O nome de usuário e a senha não podem estar vazios.")
            }

            if (isAdmin(alias)) {
                val sql = "SELECT COUNT(*) FROM users WHERE alias=? AND cpf=?"

                try {
                    val preparedStatement = connection.prepareStatement(sql)
                    preparedStatement.setString(1, alias)
                    preparedStatement.setString(2, cpf)
                    val resultSet = preparedStatement.executeQuery()
                    resultSet.next()
                    val count = resultSet.getInt(1)

                    resultSet.close()
                    preparedStatement.close()

                    if (count > 0) {
                        return true
                    } else {
                        throw IllegalArgumentException("Credenciais de administrador inválidas.")
                    }
                } catch (e: SQLException) {
                    e.printStackTrace()
                    throw RuntimeException("Erro ao verificar as credenciais do usuário.")
                }
            } else {
                throw IllegalArgumentException("Acesso não autorizado. Você não é um administrador.")
            }
        }

        fun isValidUserCredentials(alias: String, cpf: String): Boolean {
            if (alias.isBlank() || cpf.isBlank()) {
                println("O nome de usuário e a senha não podem estar vazios.")
                return false
            }

            val sql = "SELECT COUNT(*) FROM users WHERE alias=? AND cpf=?"

            try {
                val preparedStatement = connection.prepareStatement(sql)
                preparedStatement.setString(1, alias)
                preparedStatement.setString(2, cpf)
                val resultSet = preparedStatement.executeQuery()
                resultSet.next()
                val count = resultSet.getInt(1)

                resultSet.close()
                preparedStatement.close()

                return count > 0
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return false
        }
    }

}