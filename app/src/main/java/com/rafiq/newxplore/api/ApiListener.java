package com.rafiq.newxplore.api;

import com.rafiq.newxplore.model.BlogBodyModel;
import com.rafiq.newxplore.model.Category;
import com.rafiq.newxplore.model.TitleModel;

import java.util.List;

public class ApiListener {
    public interface TitleDownloadListener {
        void onTitleDownloadSuccess(List<TitleModel> list);

        void onTitleDownloadFailed(String msg);
    }

    public interface ParentCategoryDownloadListener {
        void onParentCategoryDownloadSuccess(List<Category> list);

        void onParentCategoryDownloadFailed(String msg);
    }

    public interface BlogBodyDownloadListener {
        void onBlogBodyDownloadSuccess(BlogBodyModel data);

        void onBlogBodyDownloadFailed(String msg);
    }
}
