package app.khodko.planner

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule


private const val GLIDE_MEMORY_CACHE = 10L * 1024 * 1024
private const val GLIDE_DISK_CACHE = 100L * 1024 * 1024

@GlideModule
class PlannerGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setMemoryCache(LruResourceCache(GLIDE_MEMORY_CACHE))
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, GLIDE_DISK_CACHE))
    }

}