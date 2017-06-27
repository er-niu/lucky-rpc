package builder;

import com.lucky.db.sqlbuilder.SQL;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:15 2017/6/26
 */
public class SqlTest {


    public static String getSql() {
        final StringBuilder sb = new StringBuilder();
        final String sql = new SQL() {{
            SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FULL_NAME");
            SELECT("P.LAST_NAME, P.CREATED_ON, P.UPDATED_ON");
            FROM("PERSON P");
            FROM("ACCOUNT A");
            INNER_JOIN("DEPARTMENT D on D.ID = P.DEPARTMENT_ID");
            INNER_JOIN("COMPANY C on D.COMPANY_ID = C.ID");
            WHERE("P.ID = A.ID");
            WHERE("P.FIRST_NAME like ?");
            OR();
            WHERE("P.LAST_NAME like ?");
            GROUP_BY("P.ID");
            HAVING("P.LAST_NAME like ?");
            OR();
            HAVING("P.FIRST_NAME like ?");
            ORDER_BY("P.ID");
            ORDER_BY("P.FULL_NAME");
            LIMIT("1", "2");
        }}.usingAppender(sb).toString();
        return sql;
    }

    public static String getSql2() {
        final String sql = new SQL() {{
            SELECT("id, name");
            SELECT("count(*)");
            FROM("PERSON A");
            WHERE("name like ?").WHERE("id = ?");
        }}.toString();
        return sql;
    }


    public static String test1() {
        SQL sql = new SQL();
        sql.VALUES("aa", "bb").VALUES("asdf", "asdf").INSERT_INTO("table");
        return sql.toString();
    }

    public static String test3() {
        SQL sql = new SQL();
//        sql.UPDATE("dd").SET.WHERE("AS =?");
        sql.UPDATE("sd").SET("aa=?").SET("ss=?").WHERE("sdf");
        return sql.toString();
    }

    public static String test6() {
        SQL sql = new SQL();
//        sql.UPDATE("dd").SET.WHERE("AS =?");
        sql.DELETE_FROM("sdf").WHERE("234 like 2");
        return sql.toString();
    }

    public static String test4() {
        SQL sql = new SQL();
        sql.DELETE_FROM("23").WHERE("a,like");
        return sql.toString();
    }

    private static String example2(final String id, final String firstName, final String lastName) {
        return new SQL() {{
            SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FIRST_NAME, P.LAST_NAME");
            FROM("PERSON P");
            if (id != null) {
                WHERE("P.ID like #id#");
            }
            if (firstName != null) {
                WHERE("P.FIRST_NAME like #firstName#");
            }
            if (lastName != null) {
                WHERE("P.LAST_NAME like #lastName#");
            }
            ORDER_BY("P.LAST_NAME");
        }}.toString();
    }

    public static void main(String[] args) {
//        System.out.println(getSql());
        System.out.println(getSql2());
//        System.out.println(example2("1", "2", "3"));
//        System.out.println(test1());
//        System.out.println(test3());
//        System.out.println(test6());
    }
}
