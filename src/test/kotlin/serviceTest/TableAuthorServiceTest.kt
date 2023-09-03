package serviceTest

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import service.tableAuthorService.TableAuthorService
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement
import kotlin.test.assertEquals

class TableAuthorServiceTest {

    private val tableAuthorService = TableAuthorService()

    // Mock para a conexão de banco de dados
    private val connection = mock(Connection::class.java)

    // Mock para o Statement
    private val statement = mock(Statement::class.java)

    // Mock para o ResultSet
    private val resultSet = mock(ResultSet::class.java)

    // Redireciona a saída padrão para capturar as mensagens impressas durante os testes.
    private val standardOut = System.out
    private val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        // Redireciona a saída padrão para o outputStreamCaptor.
        System.setOut(PrintStream(outputStreamCaptor))

        // Injete os mocks de conexão e statement na classe de serviço
    //    tableAuthorService.setConnection(connection)

        // Configura o comportamento dos mocks
        `when`(connection.createStatement()).thenReturn(statement)
    }

    @Test
    fun testDeleteAuthor() {
        // Configura o comportamento do mock para a exclusão de autor
        `when`(statement.executeUpdate(anyString())).thenReturn(1)

        // Execute o teste
        tableAuthorService.deleteAuthor(1) // Substitua pelo ID válido de um autor

        // Verifica se a mensagem de sucesso foi impressa.
        assertEquals("Autor deletado com sucesso!\n", outputStreamCaptor.toString())
    }

    @Test
    fun testUpdateAuthor() {
        // Configura o comportamento do mock para a atualização de autor
        `when`(statement.executeUpdate(anyString())).thenReturn(1)

        // Execute o teste
        tableAuthorService.updateAuthor(1, "UpdatedAuthor") // Substitua pelo ID válido de um autor

        // Verifica se a mensagem de sucesso foi impressa.
        assertEquals("Autor atualizado com sucesso!\n", outputStreamCaptor.toString())
    }

    @Test
    fun testListAuthors() {
        // Configura o comportamento do mock para listar autores
        `when`(statement.executeQuery(anyString())).thenReturn(resultSet)
        `when`(resultSet.next()).thenReturn(true, true, false) // Duas linhas de resultados

        // Configura os resultados simulados
        `when`(resultSet.getInt("id")).thenReturn(1, 2)
        `when`(resultSet.getString("name_author")).thenReturn("Author1", "Author2")

        // Execute o teste
        tableAuthorService.listAuthors()

        // Verifica se as mensagens contêm os nomes dos autores.
        val output = outputStreamCaptor.toString()
        assertTrue(output.contains("Author1"))
        assertTrue(output.contains("Author2"))
    }

    @Test
    fun testListSpecificAuthor() {
        // Configura o comportamento do mock para listar um autor específico
        `when`(statement.executeQuery(anyString())).thenReturn(resultSet)
        `when`(resultSet.next()).thenReturn(true, false) // Uma linha de resultado

        // Configura o resultado simulado
        `when`(resultSet.getInt("id")).thenReturn(1)
        `when`(resultSet.getString("name_author")).thenReturn("Author1")

        // Execute o teste
        tableAuthorService.listSpecificAuthor(1) // Substitua pelo ID válido de um autor

        // Verifica se a mensagem contém o nome do autor.
        val output = outputStreamCaptor.toString()
        assertTrue(output.contains("Author1"))
    }
}

