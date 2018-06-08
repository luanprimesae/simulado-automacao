package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	public static String status = "Não conectou.";
	static Connection connection = null;
	
	public Database() {

	}

	// Método de Conexão
	public static java.sql.Connection getConnectionMySQL() {

		try {

			String driverName = "com.mysql.jdbc.Driver";

			Class.forName(driverName);

			// Configurando a nossa conexão com um banco de dados
			String serverName = "db.simulado.dev.sae.digital"; // caminho do servidor do BD
			String mydatabase = "simuladov2"; // nome do seu banco de dados
			String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
			String username = "qa"; // nome de um usuário de seu BD
			String password = "qa@Sae2018"; // sua senha de acesso

			connection = DriverManager.getConnection(url, username, password);

			// Testa sua conexão
			if (connection != null) {
				status = ("Conectado com sucesso!");
				
			} else {
				status = ("Não foi possivel realizar conexão.");
				
			}

			return connection;

		} catch (ClassNotFoundException e) {

			System.out.println("O driver nao encontrado.");

			return null;

		} catch (SQLException e) {

			System.out.println("Nao foi possivel conectar ao Banco de Dados.");

			return null;

		}

	}

	// Método retorno status da conexão
	public static String connectionStatus() {
		return status;
	}

	// Método fechamento da conexão
	public static boolean closeConnection() {
		try {

			Database.getConnectionMySQL().close();
			return true;

		} catch (SQLException e) {

			return false;

		}

	}
	
	// Método para verificar se o usuario ja existe cadastrado no banco
	public static boolean selectUser() {
		Database.getConnectionMySQL();
		Elements elements = new Elements();
		
		String sql = "SELECT * FROM auth_user WHERE email LIKE '"+elements.getEmail()+"'";
		
		try {
			java.sql.Statement statement = connection.createStatement();
			java.sql.ResultSet result = statement.executeQuery(sql);
			
			while(result.next()) {
				String email = result.getString(5);
				
				if(email.equals(elements.getEmail())) {
					return true;	
				}
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}

}