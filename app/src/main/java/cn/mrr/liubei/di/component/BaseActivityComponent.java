package cn.mrr.liubei.di.component;


import cn.mrr.liubei.activity.MainActivity;
import cn.mrr.liubei.di.scope.ActivityScope;
import cn.mrr.liubei.module.BaseActivityModule;
import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = BaseActivityModule.class)
public interface BaseActivityComponent { //设置文件组成关联，有几个需要的Activity添加几个
    void injectMainActivity(MainActivity activity);


}
