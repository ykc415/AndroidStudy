package kr.co.fastcampus.mvp_pattern.main;

public interface MainPresenter {

    void onResume();

    void onItemClicked(int position);

    void onDestroy();
}
