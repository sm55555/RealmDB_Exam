package com.example.osangmin.realmdb_exam;
//여기서 중요한 수정 사항을 빼먹음 ㅠㅠㅠ
import io.realm.RealmObject;

/**
 * Created by junsuk on 2017. 3. 14..
 */

public class User extends RealmObject {

    //생성자 기본 메소드
    public User(){

    }

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}