package kr.co.fastcampus.mvp_pattern.login;

public interface LoginPresenter {
    void validateCredentials(String username, String password);

    void onDestroy();
}
