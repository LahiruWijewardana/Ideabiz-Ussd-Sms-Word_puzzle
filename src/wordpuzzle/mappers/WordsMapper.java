package wordpuzzle.mappers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import wordpuzzle.dbconnect.DatabaseConnect;
import wordpuzzle.models.Words;

public class WordsMapper {

	public ArrayList<Words> getWords() throws SQLException {
		
		DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();

        String query = "SELECT * from Words";
        
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(query);
        
        ArrayList<Words> wordsList = new ArrayList<Words>();

        while (rs.next()) {
        		Words word = new Words();
        		word.setId(rs.getInt("id"));
        		word.setWord(rs.getString("word"));
        		
        		wordsList.add(word);
        }

        con.close();
        
        return wordsList;
	}
}
