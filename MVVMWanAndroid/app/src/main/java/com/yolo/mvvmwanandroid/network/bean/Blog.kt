package com.yolo.mvvmwanandroid.network.bean

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * @author yolo.huang
 * @date 2020/8/20
 */
@Parcelize
@Entity
data class Blog (
    @PrimaryKey(autoGenerate = true)
    var primaryKeyId: Int = 0,
    var apkLink: String? = "",
    var audit: Int = 0,
    var author: String? = "",
    var chapterId: Int = 0,
    var chapterName: String? = "",
    var collect: Boolean = false,
    var courseId: Int = 0,
    var desc: String? = "",
    var envelopePic: String? = "",
    var fresh: Boolean = false,
    var id: Int = 0,
    var link: String? = "",
    var niceDate: String? = "",
    var niceShareDate: String? = "",
    var origin: String? = "",
    var originId: Int = 0,
    var prefix: String? = "",
    var projectLink: String? = "",
    var publishTime: Long = 0,
    var selfVisible: Int = 0,
    var shareDate: Long = 0,
    var shareUser: String? = "",
    var superChapterId: Int = 0,
    var superChapterName: String? = "",
    @Ignore
    var tags: List<Tag> = emptyList(),
    var title: String? = "",
    var type: Int = 0,
    var userId: Int = 0,
    var visible: Int = 0,
    var zan: Int = 0,
    var top: Boolean = false
): Parcelable{

    companion object{
        fun fromMap(map:Map<String,Any>):Blog{
            val blog = Blog()
            blog.title = map["title"] as String
            blog.collect = map["collect"] as Boolean
            blog.desc = map["desc"] as String
            blog.id = map["id"] as Int
            blog.link = map["link"] as String
/*            blog.apkLink = map["apkLink"] as String
            blog.audit = map["audit"] as Int
            blog.author = map["author"] as String
            blog.chapterId = map["chapterId"] as Int
            blog.chapterName = map["chapterName"] as String
            blog.courseId = map["courseId"] as Int
            blog.envelopePic = map["envelopePic"] as String
            blog.niceDate = map["niceDate"] as String
            blog.niceShareDate = map["niceShareDate"] as String
            blog.origin = map["origin"] as String
            blog.originId = map["originId"] as Int
            blog.prefix = map["prefix"] as String
            blog.publishTime = map["publishTime"] as Long
            blog.projectLink = map["projectLink"] as String
            blog.selfVisible = map["selfVisible"] as Int
            blog.shareDate = map["shareDate"] as Long
            blog.shareUser = map["shareUser"] as String
            blog.superChapterId = map["superChapterId"] as Int
            blog.superChapterName = map["superChapterName"] as String
            blog.type = map["type"] as Int
            blog.userId = map["userId"] as Int
            blog.visible = map["visible"] as Int
            blog.zan = map["zan"] as Int
            blog.fresh = map["fresh"] as Boolean*/
            return blog
        }
    }



}
