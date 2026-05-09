package cn.mrr.liubei.base.interfaces;

/**
 * @author 53443
 */
public interface BasePresenter<T extends BaseView> {
    /**
     * 绑定View
     *
     * @param view
     */
    void attachView(T view);

    /**
     * 解绑View
     */
    void detachView();
}
