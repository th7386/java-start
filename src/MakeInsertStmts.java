import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

 
public class MakeInsertStmts {

    private ArrayList<String> fileNameList;
    private ArrayList<String> tableNameList;


    public MakeInsertStmts() {
        fileNameList = new ArrayList<String>();
        tableNameList = new ArrayList<String>();
    }


    public void addFile(String fileName, String tableName) {
        fileNameList.add(fileName);
        tableNameList.add(tableName);
    }

    // This function converts each line to the INSERT INTO statement.
    // Please handle NULL accordingly.
    private String convert(String line, String tableName) {
        String[] values = line.split(",");

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(tableName).append(" VALUES (");

        for (int i = 0; i < values.length; i++) {
            String value = values[i].trim();

            // 닉네임이 "null"인 경우
            if (i == 0 && value.equals("null")) {
                sql.append("'null'");
            } else {
                if (value.equalsIgnoreCase("NULL")) {
                    sql.append("NULL");
                } else {
                    sql.append("'").append(value.replace("'", "''")).append("'");
                }
            }

            if (i < values.length - 1) {
                sql.append(", ");
            }
        }

        sql.append(");");
        return sql.toString();
    }



    // This function reads the table-like file, converts each line to SQL
    // and returns the SQL string which contains a set of SQL statememts.
    public String convertToSQLs(String fileName, String tableName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8));
            String line = br.readLine();  // Skip the header
            String sqls = "";
            while( (line=br.readLine()) != null) {
                sqls = sqls + convert(line, tableName) + "\n";
            }

            return sqls;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    } 

   

    public void makeSQLFile(String outputFileName) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName, StandardCharsets.UTF_8));

            int len = fileNameList.size();
            for (int i=0; i<len; i++) {
                String sqls = convertToSQLs(fileNameList.get(i), tableNameList.get(i));
                bw.write(sqls);
            }

            bw.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    

    public static void main(String [] args) {
        MakeInsertStmts make = new MakeInsertStmts();
        make.addFile("student.csv", "STUDENT");
        make.addFile("planguage.csv", "PLANGUAGE");
        make.addFile("course.csv", "COURSE");
        make.addFile("professor.csv", "PROFESSOR");
        make.addFile("preferred_pl.csv", "PREFERRED_PL");
        make.addFile("favorite_course.csv", "FAVORITE_COURSE");
        make.addFile("favorite_food.csv", "FAVORITE_FOOD");
        
        make.makeSQLFile("TOTAL.sql");

    }

}
