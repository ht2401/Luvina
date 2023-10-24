package com.luvina.la.until;

public class MessageConstant {
    public static final String CODE_200 = "200";
    public static final String CODE_400 = "400";
    public static final String CODE_500 = "500";
    public static final String CODE_MSG001 = "MSG001";
    public static final String CODE_MSG002 = "MSG002";
    public static final String CODE_MSG003 = "MSG003";
    public static final String CODE_MSG004 = "MSG004";
    public static final String CODE_MSG005 = "MSG005";
    public static final String CODE_ER001 = "ER001";
    public static final String CODE_ER002 = "ER002";
    public static final String CODE_ER003 = "ER003";
    public static final String CODE_ER004 = "ER004";
    public static final String CODE_ER005 = "ER005";
    public static final String CODE_ER006 = "ER006";
    public static final String CODE_ER007 = "ER007";
    public static final String CODE_ER008 = "ER008";
    public static final String CODE_ER009 = "ER009";
    public static final String CODE_ER010 = "ER010";
    public static final String CODE_ER011 = "ER011";
    public static final String CODE_ER012 = "ER012";
    public static final String CODE_ER013 = "ER013";
    public static final String CODE_ER014 = "ER014";
    public static final String CODE_ER015 = "ER015";
    public static final String CODE_ER016 = "ER016";
    public static final String CODE_ER017 = "ER017";
    public static final String CODE_ER018 = "ER018";
    public static final String CODE_ER019 = "ER019";
    public static final String CODE_ER020 = "ER020";
    public static final String CODE_ER021 = "ER021";
    public static final String CODE_ER022 = "ER022";
    public static final String CODE_ER023 = "ER023";
    // Không nhập: Tên hạng mục + ER001
    public static final String ER001 = "を入力してください";
    // Không chọn: Tên hạng mục + ER002
    public static final String ER002 = "を入力してください";
    // Check đã tồn tại: Tên hạng mục + ER003
    public static final String ER003 = "は既に存在しています。";
    // Check không tồn tại: Tên hạng mục + ER004
    public static final String ER004 = "は存在していません。";
    // Sai format: Tên hạng mục + ER005
    public static final String ER005 = "をxxx形式で入力してください";

    public static final String ER005_DATE = "をyyyy/MM/dd形式で入力してください";
    public static final String ER005_EMAIL = "をemail形式で入力してください";
    // Check maxlength
    public static final String ER006 = "xxxx桁以内の「画面項目名」を入力してください";

    public static final String ER006_EMPLOYEE_LOGIN_ID = "50桁以内の「アカウント名」を入力してください";

    public static final String ER006_EMPLOYEE_NAME = "125桁以内の「氏名」を入力してください";

    public static final String ER006_EMPLOYEE_EMAIL = "125桁以内の「メールアドレス」を入力してください";

    public static final String ER006_EMPLOYEE_TELEPHONE = "125桁以内の「電話番号」を入力してください";
    // Check độ dài trong khoảng: Tên hạng mục + ER007
    public static final String ER007 = "をxxx＜＝桁数、＜＝xxx桁で入力してください";

    public static final String ER007_LOGIN_PASSWORD = "を8＜＝桁数、＜＝50桁で入力してください";
    // Check ký tự 1 byte: Tên hạng mục + ER008
    public static final String ER008 = "に半角英数を入力してください";

    public static final String ER008_TELEPHONE = "に半角英数を入力してください";
    // Check kana: Tên hạng mục + ER009
    public static final String ER009 = "をカタカナで入力してください";
    // Check hiragana: Tên hạng mục + ER010
    public static final String ER010 = "をひらがなで入力してください";
    // Ngày không hợp lệ: Tên hạng mục + ER011
    public static final String ER011 = "は無効になっています。";
    // Ngày hết hạn < ngày cấp chứng chỉ
    public static final String ER012 = "「失効日」は「資格交付日」より未来の日で入力してください。";
    // Biên tập user không tồn tại
    public static final String ER013 = "該当するユーザは存在していません。";
    // Xóa user không tồn tại
    public static final String ER014 = "該当するユーザは存在していません。";
    // Lỗi khi thao tác với database
    public static final String ER015 = "システムエラーが発生しました。";
    // Nhập sai Tên đăng nhập hoặc Password
    public static final String ER016 = "「アカウント名」または「パスワード」は不正です。";
    // Mật khẩu xác nhận không đúng
    public static final String ER017 = "「パスワード（確認」が不正です。";
    // Check phải là số halfsize
    public static final String ER018 = "「画面上の項目名」は半角で入力してください。";
    // Tên đăng nhập không đúng định dạng
    public static final String ER019 = "[アカウント名]は(a-z, A-Z, 0-9 と _)の桁のみです。最初の桁は数字ではない。";
    // Kiểm tra user admin
    public static final String ER020 = "管理者ユーザを削除することはできません。";
    // Kiểm tra thứ tự sắp xếp
    public static final String ER021 = "ソートは (ASC, DESC) でなければなりません。";
    // User di chuyển đến trang không tồn tại
    public static final String ER022 = "ページが見つかりません。";
    // Lỗi hệ thống
    public static final String ER023 = "システムエラーが発生しました。";
    // Đăng ký User thành công
    public static final String MSG001 = "ユーザの登録が完了しました。";
    // Update User thành công
    public static final String MSG002 = "ユーザの更新が完了しました。";
    // Delete User thành công
    public static final String MSG003 = "ユーザの削除が完了しました。";
    // Xác nhận trước khi xóa
    public static final String MSG004 = "削除しますが、よろしいでしょうか。";
    // Không tim thấy user
    public static final String MSG005 = "検索条件に該当するユーザが見つかりません。";
}
