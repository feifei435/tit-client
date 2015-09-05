package cn.edu.tit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import cn.edu.tit.bean.City;
import cn.edu.tit.bean.WeatherInfo;
import cn.edu.tit.config.Config;
import cn.edu.tit.db.CityDB;
import cn.edu.tit.util.NetUtil;
import cn.edu.tit.util.SharePreferenceUtil;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class Application extends android.app.Application {
	public static final int CITY_LIST_SCUESS = 100;
	private static Application mApplication;
	private HashMap<String, Integer> mWeatherIcon;// 天气图标
	private HashMap<String, Integer> mWidgetWeatherIcon;// 插件天气图标
	// 首字母集
	private List<String> mSections;
	// 根据首字母存放数据
	// 首字母位置集
	private List<Integer> mPositions;
	// 首字母对应的位置
	private Map<String, Integer> mIndexer;
	private CityDB mCityDB;

	private SharePreferenceUtil mSpUtil;
	public static int mNetWorkState;
	private NotificationManager mNotificationManager;

	public static synchronized Application getInstance() {
		return mApplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mApplication = this;
		initImageLoader(getApplicationContext());
		mCityDB = openCityDB();// 这个必须最先复制完,所以我放在单线程中处理,待优化
		initData();
	}

	public void initData() {
		mNetWorkState = NetUtil.getNetworkState(this);
		initCityList();
		mSpUtil = new SharePreferenceUtil(this,
				SharePreferenceUtil.CITY_SHAREPRE_FILE);
		mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
	}

	public synchronized SharePreferenceUtil getSharePreferenceUtil() {
		if (mSpUtil == null)
			mSpUtil = new SharePreferenceUtil(this,
					SharePreferenceUtil.CITY_SHAREPRE_FILE);
		return mSpUtil;
	}

	/** 初始化ImageLoader */
	public static void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"titnews/Cache");// 获取到缓存的目录地址
		Log.d("cacheDir", cacheDir.getPath());
		// 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				// .memoryCacheExtraOptions(480, 800) // max width, max
				// height，即保存的每个缓存文件的最大长宽
				// .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75,
				// null) // Can slow ImageLoader, use it carefully (Better don't
				// use it)设置缓存的详细信息，最好不要设置这个
				.threadPoolSize(3)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				// .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 *
				// 1024)) // You can pass your own memory cache
				// implementation你可以通过自己的内存缓存实现
				// .memoryCacheSize(2 * 1024 * 1024)
				// /.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())// 将保存的时候的URI名称用MD5
																		// 加密
				// .discCacheFileNameGenerator(new
				// HashCodeFileNameGenerator())//将保存的时候的URI名称用HASHCODE加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// .discCacheFileCount(100) //缓存的File数量
				.discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
				// .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				// .imageDownloader(new BaseImageDownloader(context, 5 * 1000,
				// 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);// 全局初始化此配置
	}

	private City city;

	public City getCity() {
		if (city != null) {
			return city;
		}
		if (!mSpUtil.getCity().endsWith("")) {
			return mCityDB.getCity(mSpUtil.getCity());
		}

		return new City("山西", "太原", "101100101", "taiyuan", "ty");
	}

	public void setCity(City city) {
		this.city = city;
		mSpUtil.setCity(city.getName());
	}

	private WeatherInfo allWeather;

	public WeatherInfo getAllWeather() {
		return allWeather;
	}

	public void setAllWeather(WeatherInfo allWeather) {
		this.allWeather = allWeather;
	}

	private List<City> mCityList;
	// 根据首字母存放数据
	private Map<String, List<City>> mMap;

	private CityDB openCityDB() {
		String path = "/data"
				+ Environment.getDataDirectory().getAbsolutePath()
				+ File.separator +Config.DEFAULT_DB_PARENT_PATH + File.separator
				+ CityDB.CITY_DB_NAME;
		File db = new File(path);
		if (!db.exists() || getSharePreferenceUtil().getVersion() < 0) {
			try {
				InputStream is = getAssets().open(CityDB.CITY_DB_NAME);
				FileOutputStream fos = new FileOutputStream(db);
				int len = -1;
				byte[] buffer = new byte[1024];
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
					fos.flush();
				}
				fos.close();
				is.close();
				getSharePreferenceUtil().setVersion(1);// 用于管理数据库版本，如果数据库有重大更新时使用
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		return new CityDB(this, path);
	}

	public List<City> getCityList() {
		return mCityList;
	}

	public List<String> getSections() {
		return mSections;
	}

	public Map<String, List<City>> getMap() {
		return mMap;
	}

	public List<Integer> getPositions() {
		return mPositions;
	}

	public Map<String, Integer> getIndexer() {
		return mIndexer;
	}

	// 当程序在后台运行时，释放这部分最占内存的资源
	public void free() {
		mCityList = null;
		mSections = null;
		mMap = null;
		mPositions = null;
		mIndexer = null;
		mWeatherIcon = null;
		System.gc();
	}

	private boolean prepareCityList() {
		mCityList = new ArrayList<City>();
		mSections = new ArrayList<String>();
		mMap = new HashMap<String, List<City>>();
		mPositions = new ArrayList<Integer>();
		mIndexer = new HashMap<String, Integer>();
		mCityList = mCityDB.getAllCity();// 获取数据库中所有城市
		for (City city : mCityList) {
			String firstName = city.getPy().substring(0, 1).toUpperCase();// 第一个字拼音的第一个字母
			if (firstName.matches(Config.FORMAT)) {
				if (mSections.contains(firstName)) {
					mMap.get(firstName).add(city);
				} else {
					mSections.add(firstName);
					List<City> list = new ArrayList<City>();
					list.add(city);
					mMap.put(firstName, list);
				}
			} else {
				if (mSections.contains("#")) {
					mMap.get("#").add(city);
				} else {
					mSections.add("#");
					List<City> list = new ArrayList<City>();
					list.add(city);
					mMap.put("#", list);
				}
			}
		}
		Collections.sort(mSections);// 按照字母重新排序
		int position = 0;
		for (int i = 0; i < mSections.size(); i++) {
			mIndexer.put(mSections.get(i), position);// 存入map中，key为首字母字符串，value为首字母在listview中位置
			mPositions.add(position);// 首字母在listview中位置，存入list中
			position += mMap.get(mSections.get(i)).size();// 计算下一个首字母在listview的位置
		}
		return true;
	}

	private void initCityList() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				prepareCityList();
			}
		}).start();
	}

}
