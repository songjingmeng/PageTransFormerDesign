package com.test.jingmengsong.pageandcolorformer.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Looper
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.test.jingmengsong.pageandcolorformer.R

/**
 * 业务名：
 * 功能说明：
 * 创建于：2018/9/10 on 14:57
 * 作者： jingmengsong
 * <p/>
 * 历史记录
 * 修改日期：
 * 修改人：
 * 修改内容：
 */
class PageLayout : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    // 枚举类
    enum class State {
        EMPTY_TYPE,
        LOADING_TYPE,
        ERROR_TYPE,
        CONTENT_TYPE
    }

    private var mCurrentState = State.CONTENT_TYPE
    private var mLoading: View? = null
    private var mEmpty: View? = null
    private var mError: View? = null
    private var mContent: View? = null
    private var mContext: Context? = null
    private var mBlinkLayout: BlinkLayout? = null


    // -----------------------------   外部类中的方法 供外部调用 不做具体内部的操作 ------------------------
    /**
     *  展示view
     *  myLooper（）  返回与当前线程关联的Looper对象。如果调用线程未与Looper关联，则返回null。
     *  getMainLooper（）返回应用程序的主循环器，它位于应用程序的主线程中。
     */
    private fun showView(type: State) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            changeView(type)
        } else {
            post {
                changeView(type)
            }
        }
    }

    /**
     *  根据传入的类型进行判断 切换为 哪个布局展示
     */
    private fun changeView(type: State) {
        //记录当前的状态 默认当前状态是展示内容的状态
        mCurrentState = type
        //根据类型， view进行展示状态
        mLoading?.visibility = if (type == State.LOADING_TYPE) View.VISIBLE else View.GONE
        mEmpty?.visibility = if (type == State.EMPTY_TYPE) View.VISIBLE else View.GONE
        mError?.visibility = if (type == State.ERROR_TYPE) View.VISIBLE else View.GONE
        mContent?.visibility = if (type == State.CONTENT_TYPE) View.VISIBLE else View.GONE
    }


    /**
     * 展示 加载进度
     */
    fun showLoading() {
        showView(State.LOADING_TYPE)
        //TODO 进行展示加载进度的blinkLayout 开始动画
        mBlinkLayout?.apply {
            mBlinkLayout!!.startShimmerAnimation()
        }
    }

    /**
     * 展示 网络加载错误 的界面
     */
    fun showError() {
        showView(State.ERROR_TYPE)
    }

    /**
     * 展示 空数据 的页面
     */
    fun showEmpty() {
        showView(State.EMPTY_TYPE)
    }

    /**
     * 展示 内容 页面
     */
    fun hide() {
        showView(State.CONTENT_TYPE)
        //TODO 进行展示加载进度的blinkLayout 结束动画
        mBlinkLayout?.apply {
            mBlinkLayout!!.stopShimmerAnimation()
        }
    }


    class Builder {

        private var mPageLayout: PageLayout
        private var mInflater: LayoutInflater
        private var mContext: Context
        private lateinit var mTvEmpty: TextView
        private lateinit var mTvError: TextView
        private var mOnRetryClickListener: OnRetryClickListener? = null
        private lateinit var mBlinkLayout: BlinkLayout
        private lateinit var mTvLoading: TextView
        private lateinit var mTvLoadingBlink: TextView


        /**
         *  builder 的构造函数
         */
        constructor(context: Context) {
            this.mContext = context
            //获取外部类 对象
            this.mPageLayout = PageLayout(context)
            //获取填充对象
            mInflater = LayoutInflater.from(context)
        }


        /**
         *  为各种切换布局 添加 布局内容 view
         */
        private fun initDefault() {

            if (mPageLayout.mEmpty == null) {
                setDefaultEmpty()
            }
            if (mPageLayout.mError == null) {
                setDefaultError()
            }
            if (mPageLayout.mLoading == null) {
                setDefaultLoading()
            }
        }


        /**
         *  设置默认的 空数据 布局
         */
        private fun setDefaultEmpty() {
            mPageLayout.mEmpty = mInflater.inflate(R.layout.layout_empty, mPageLayout, false)
                    .apply {
                        mTvEmpty = findViewById<TextView>(R.id.tv_page_empty)
                    }
            //将空数据 view 添加 到 容器 mPageLayout
            mPageLayout.mEmpty?.visibility = View.GONE
            mPageLayout.addView(mPageLayout.mEmpty)
        }

        /**
         *  设置默认的 网络错误 布局
         */
        private fun setDefaultError() {
            mPageLayout.mError = mInflater.inflate(R.layout.layout_error, mPageLayout, false)
                    .apply {
                        mTvError = findViewById<TextView>(R.id.tv_page_error)
                    }
            mPageLayout.mError?.visibility = View.GONE
            mPageLayout.addView(mPageLayout.mError)
        }

        /**
         *  设置默认的 加载进度 布局
         */
        private fun setDefaultLoading() {
            mPageLayout.mLoading = mInflater.inflate(R.layout.layout_loading, mPageLayout, false)
                    .apply {
                        mBlinkLayout = findViewById<BlinkLayout>(R.id.blinklayout)
                        mPageLayout.mBlinkLayout = mBlinkLayout
                        mTvLoading = findViewById<TextView>(R.id.tv_page_loading)
                        mTvLoadingBlink = findViewById(R.id.tv_page_loading_blink)
                    }
            mPageLayout.mLoading?.visibility = View.GONE
            mPageLayout.addView(mPageLayout.mLoading)

        }


        //--------------------------------------------  自定义加载界面的样式 ------------------------------------
        fun setLoading(loading: Int): Builder {
            mInflater.inflate(loading, mPageLayout, false).apply {
                mPageLayout.mLoading = this
                mPageLayout.addView(this)
            }
            return this
        }

        fun setError(errorView: Int, errorClickId: Int, onRetryClickListener: OnRetryClickListener): Builder {
            mInflater.inflate(errorView, mPageLayout, false).apply {
                mPageLayout.mError = this
                mPageLayout.addView(this)
                mTvError = findViewById(errorClickId)
                mTvError.setOnClickListener { onRetryClickListener.onRetry() }
            }
            return this
        }

        fun setError(errorView: View): Builder {
            mPageLayout.mError = errorView
            mPageLayout.addView(errorView)
            return this
        }


        fun setEmpty(empty: Int, emptyTvId: Int): Builder {
            mInflater.inflate(empty, null, false).apply {
                mTvEmpty = findViewById(emptyTvId)
                mPageLayout.mEmpty = this
                mPageLayout.addView(this)
            }
            return this
        }

        fun setDefaultLoadingText(text: String): Builder {
            mTvLoading.text = text
            return this
        }

        fun setDefaultLoadingBlinkText(text: String): Builder {
            mTvLoadingBlink.text = text
            return this
        }

        fun setDefaultLoadingTextColor(color: Int): Builder {
            mTvLoading.setTextColor(mContext.resources.getColor(color))
            return this
        }

        fun setDefaultLoadingBlinkColor(color: Int): Builder {
            mBlinkLayout.setShimmerColor(mContext.resources.getColor(color))
            return this
        }


        fun setDefaultEmptyText(text: String): Builder {
            mTvEmpty.text = text
            return this
        }

        fun setDefaultEmptyTextColor(color: Int): Builder {
            mTvEmpty.setTextColor(mContext.resources.getColor(color))
            return this
        }

        fun setDefaultErrorText(text: String) {
            mTvError.text = text
        }

        fun setDefaultErrorTextColor(color: Int) {
            mTvError.setTextColor(mContext.resources.getColor(color))
        }


        /**
         * 为默认 空数据 布局 添加上部图片
         */
        fun setEmptyDrawable(resId: Int): Builder {
            setTopDrawables(mTvEmpty, resId)
            return this
        }

        /**
         * 为默认 网络错误 布局 添加上部图片
         */
        fun setErrorDrawable(resId: Int): Builder {
            setTopDrawables(mTvError, resId)
            return this
        }


        fun setOnRetryListener(onRetryClickListener: OnRetryClickListener): Builder {
            this.mOnRetryClickListener = onRetryClickListener
            return this
        }

        private fun setTopDrawables(textView: TextView, resId: Int) {
            //判断资源id 是否存在
            if (resId == 0) {//不存在 ，不放置图标
                textView.setCompoundDrawables(null, null, null, null)
            }

            val drawable = mContext.resources.getDrawable(resId)
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)//必须设置图片大小，否则不显示
            textView.setCompoundDrawables(null, drawable, null, null)
            textView.compoundDrawablePadding = 20
        }

        /**
         * 设置 root 的目标视图
         */
        fun initPage(targetView: Any): Builder {
            //定义 视图容器  content
            var content: ViewGroup? = null
            //判断目标视图 的 类型
            when (targetView) {
                is Activity -> {    //如果是Activity，获取到android.R.content
                    mContext = targetView
                    content = (mContext as Activity).findViewById(android.R.id.content)
                }
                is Fragment -> {    //如果是Fragment获取到parent
                    mContext = targetView.activity!!
                    content = (targetView.view)?.parent as ViewGroup
                }
                is View -> {        //如果是View，也取到parent
                    mContext = targetView.context
                    try {
                        content = (targetView.parent) as ViewGroup
                    } catch (e: TypeCastException) {
                    }
                }
            }
            val childCount = content?.childCount
            var index = 0
            val oldContent: View
            if (targetView is View) {   //如果是某个线性布局或者相对布局时，遍历它的孩子，找到对应的索引，记录下来
                oldContent = targetView
                childCount?.let {
                    for (i in 0 until childCount) {
                        if (content!!.getChildAt(i) === oldContent) {
                            index = i
                            break
                        }
                    }
                }
            } else {    //如果是Activity或者Fragment时，取到索引为第一个的View
                oldContent = content!!.getChildAt(0)
            }
            mPageLayout.mContent = oldContent   //给PageLayout设置contentView
            mPageLayout.removeAllViews()
            content?.removeView(oldContent)     //将本身content移除，并且把PageLayout添加到DecorView中去
            val lp = oldContent.layoutParams
            content?.addView(mPageLayout, index, lp)
            mPageLayout.addView(oldContent)
            initDefault()   //设置默认状态布局
            return this
        }


        fun create() = mPageLayout
    }

    interface OnRetryClickListener {
        fun onRetry()
    }


}


