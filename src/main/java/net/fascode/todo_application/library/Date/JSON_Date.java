package net.fascode.todo_application.library.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JSON収納用タイムスタンプの変換用クラス
 */
public class JSON_Date {

    final static String pattern="yyyy-MM-dd'T'hh:mm:ss.SSSZ";

    /**
     * Date型をStringにコンバートします。
     * @param dt コンバート対象
     * @return 変換後のString
     */
    public static String Date_to_String(Date dt){
        return new SimpleDateFormat(pattern).format(dt);
    }

    /**
     * String型をDate型にコンバートします。
     * @param dt コンバート対象
     * @return コンバート結果
     * @throws ParseException 変換に失敗したら発生する例外
     */
    public static Date String_to_Date(String dt) throws ParseException {
        SimpleDateFormat sdFormat = new SimpleDateFormat(pattern);
        return sdFormat.parse(dt);
    }
}
