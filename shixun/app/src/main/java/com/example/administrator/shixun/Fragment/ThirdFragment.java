package com.example.administrator.shixun.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.IPlayer;
import com.aliyun.player.nativeclass.CacheConfig;
import com.aliyun.player.source.UrlSource;
import com.bumptech.glide.Glide;
import com.example.administrator.shixun.Entity.PlayerInfo;
import com.example.administrator.shixun.Entity.Typev;
import com.example.administrator.shixun.Entity.Video;
import com.example.administrator.shixun.MainActivity;
import com.example.administrator.shixun.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

import butterknife.ButterKnife;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

import static android.view.View.VISIBLE;

;

public class ThirdFragment extends Fragment implements GestureDetector.OnGestureListener{

    private VerticalViewPager vp;
    private MyPagerAdapter myPagerAdapter;
    private int currentFlagPostion;//传递过来播放第几个
    private List<Video> list = new ArrayList<>();//播放列表
    private int mCurrentPosition;//当前正在播放第几个
    private AliPlayer aliPlayer;//当前正在播放的播放器
    private Handler handler;
    private Timer timer;

    private String videoString;
    private FrameLayout omgg;
    private TabWidget tabWidget;
    private GestureDetector detector;
    private List<Typev> abc=new ArrayList<>();
    private List<String> ggg=new ArrayList<>();
    private List<String> cde=new ArrayList<>();
    private List<String> def=new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        clearFragments();
        View view = inflater.inflate(R.layout.threefragment, container,false);
        list.clear();
        MainActivity mainActivity=(MainActivity) getActivity();

        cde.add("https://timgmb05.bdimg.com/timg?haokan&quality=100&wh_rate=0&size=b576_324&ref=http%3A%2F%2Fwww.baidu.com&sec=1576327737&di=453ae844032a3242217034f6232394a2&src=http%3A%2F%2Fvdposter.bdstatic.com%2F384824b8e6a9320c121f45a606bef682.jpeg");
        cde.add("https://timgmb.bdimg.com/timg?haokan&quality=100&wh_rate=0&size=b576_324&ref=http%3A%2F%2Fwww.baidu.com&sec=1576327961&di=23ac428b96f0a474b1d0fe2ef0d2e4c8&src=http%3A%2F%2Fvdposter.bdstatic.com%2Fd8e6ed9583fdb3c98b92f5a245b86e3f.jpeg");
        cde.add("https://timgmb.bdimg.com/timg?haokan&quality=100&wh_rate=0&size=b576_324&ref=http%3A%2F%2Fwww.baidu.com&sec=1576328065&di=c12ea71b14ffb26ff280ba168854dbdd&src=http%3A%2F%2Fvdposter.bdstatic.com%2Fb75918ef9f5c99ec0eb54729afe4d75d.jpeg");
        cde.add("https://timgmb03.bdimg.com/timg?haokan&quality=100&wh_rate=0&size=b576_324&ref=http%3A%2F%2Fwww.baidu.com&sec=1576328136&di=df8c85ffa0d66c22bf15ab81d71f3e86&src=http%3A%2F%2Fvdposter.bdstatic.com%2Fab9d37dc7e8caef19dcde89889110136.jpeg");
        cde.add("https://timgmb02.bdimg.com/timg?haokan&quality=100&wh_rate=0&size=b576_324&ref=http%3A%2F%2Fwww.baidu.com&sec=1576410500&di=4393569642c1a31c979e6bb36a3a4429&src=http%3A%2F%2Ft10.baidu.com%2Fit%2Fu%3D3277448484%2C1222697360%26fm%3D171%26s%3DBF01C80360AA810B2984ACC80300A090%26w%3D1366%26h%3D768%26img.JPEG");
        cde.add("https://timgmb01.bdimg.com/timg?haokan&quality=100&wh_rate=0&size=b576_324&ref=http%3A%2F%2Fwww.baidu.com&sec=1576410815&di=87854e3d6b639fa232e373d81afe9486&src=http%3A%2F%2Fvdposter.bdstatic.com%2Fcebfdf8e2542fc3bf5e4bb4f424650dd.jpeg");
        cde.add("https://timgmb01.bdimg.com/timg?haokan&quality=100&wh_rate=0&size=b576_324&ref=http%3A%2F%2Fwww.baidu.com&sec=1576410932&di=7bc96a6f15016a612cc69fd769fc5f52&src=http%3A%2F%2Fvdposter.bdstatic.com%2F4ccce8d84b9053e7c3d77ff881122cf5.jpeg");
        cde.add("https://timgmb03.bdimg.com/timg?haokan&quality=100&wh_rate=0&size=b576_324&ref=http%3A%2F%2Fwww.baidu.com&sec=1576411095&di=3527cd145269fdc5412c9bbebd5aa2df&src=http%3A%2F%2Ft11.baidu.com%2Fit%2Fu%3D509810527%2C795816453%26fm%3D171%26app%3D20%26f%3DJPEG%3Fw%3D832%26h%3D467%26s%3DDF0004C25A62954752CF40B50300804B");
        cde.add("https://timgmb04.bdimg.com/timg?haokan&quality=100&wh_rate=0&size=b576_324&ref=http%3A%2F%2Fwww.baidu.com&sec=1576411192&di=f80ec98d502b32249711766a807087fb&src=http%3A%2F%2Fvdposter.bdstatic.com%2F73e60820c52bfc6921fe65d84f775b99.jpeg");
        cde.add("https://timgmb05.bdimg.com/timg?haokan&quality=100&wh_rate=0&size=b576_324&ref=http%3A%2F%2Fwww.baidu.com&sec=1576411307&di=35cf799596d6f16030d8517c9a5989b8&src=http%3A%2F%2Fvdposter.bdstatic.com%2F2036f5fd2219b87363afa6d38c1fd14a.jpeg");
        def.add("番茄炒蛋");
        def.add("鱼香肉丝");
        def.add("红烧肉");
        def.add("糖醋排骨");
        def.add("鲫鱼豆腐汤");
        def.add("麻婆豆腐");
        def.add("花甲");
        def.add("土豆鸡块");
        def.add("芝麻汤圆");
        def.add("红烧茄子");
        tabWidget=mainActivity.relativeLayout;
        ggg=mainActivity.bcd;
        tabWidget.setVisibility(View.GONE);
        vp = view.findViewById(R.id.main_vp);
//        showVideo();
//        handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what){
//                    case 100:
//                        String info = (String)msg.obj.toString();
//                        videoString = info;
//                        bcd.add(videoString);
////                        cde.add(videoString);
//                        Toast.makeText(getContext(),videoString,Toast.LENGTH_SHORT).show();
//
//                        break;
//                }
//            }
//        };
        int ppp=0;
//        Typev typev=new Typev();
//        typev.setName("番茄炒蛋");
//        typev.setPic("https://timgmb05.bdimg.com/timg?haokan&quality=100&wh_rate=0&size=b576_324&ref=http%3A%2F%2Fwww.baidu.com&sec=1576327737&di=453ae844032a3242217034f6232394a2&src=http%3A%2F%2Fvdposter.bdstatic.com%2F384824b8e6a9320c121f45a606bef682.jpeg");
//        typev.setVideos("https://vdept.bdstatic.com/6970455734664564346b513432687479/683251514b667263/787d0689d1980efbd0ddf580dbae55d2e83134c888227c2bddbc7e1ab00c01c9fa3ef57c293290b58eb512c7f85f63cc.mp4?auth_key=1576384802-0-0-6a957e80053bfd8beea653a1e0e0afb3");
//        Typev typev1=new Typev();
//        typev1.setName("鱼香肉丝");
//        typev1.setPic("https://timgmb.bdimg.com/timg?haokan&quality=100&wh_rate=0&size=b576_324&ref=http%3A%2F%2Fwww.baidu.com&sec=1576327961&di=23ac428b96f0a474b1d0fe2ef0d2e4c8&src=http%3A%2F%2Fvdposter.bdstatic.com%2Fd8e6ed9583fdb3c98b92f5a245b86e3f.jpeg");
//        typev1.setVideos("https://vdept.bdstatic.com/467861384e744a313346747246474171/575942746c585345/4e9d9f5747248d8de678fc8efa28370e03605d6796b6ed243a4d01fb9e24bd4eed093cf85ac3abbd5e9c9f5f7bef987f.mp4?auth_key=1576384866-0-0-0f7618d4c189e36c32dafec9bda90187");
//        Typev typev3=new Typev();
//        typev3.setName("红烧肉");
//        typev3.setPic("https://timgmb.bdimg.com/timg?haokan&quality=100&wh_rate=0&size=b576_324&ref=http%3A%2F%2Fwww.baidu.com&sec=1576328065&di=c12ea71b14ffb26ff280ba168854dbdd&src=http%3A%2F%2Fvdposter.bdstatic.com%2Fb75918ef9f5c99ec0eb54729afe4d75d.jpeg");
//        typev3.setVideos("https://haokan.baidu.com/v?vid=14535368336060190605");
//        Typev typev4=new Typev();
//        typev4.setName("糖醋排骨");
//        typev4.setPic("https://timgmb03.bdimg.com/timg?haokan&quality=100&wh_rate=0&size=b576_324&ref=http%3A%2F%2Fwww.baidu.com&sec=1576328136&di=df8c85ffa0d66c22bf15ab81d71f3e86&src=http%3A%2F%2Fvdposter.bdstatic.com%2Fab9d37dc7e8caef19dcde89889110136.jpeg");
//        typev4.setVideos("https://vdept.bdstatic.com/6e3453786b3942635537787a6e336751/797234634358797a/0d066b38b2e5638e70a1c2064f11a9b1b55b354d7763bd1b2ca677e8067362e8f9fd569858fe85378cc2d7f6f9a3b6dd.mp4?auth_key=1576385566-0-0-b75613078f1ba0aa7888d1966cc79d98");
//        Typev typev5=new Typev();
//        typev.setName("");
//        typev.setPic("");
//        typev.setVideos("");
//        Typev typev1=new Typev();
//        typev.setName("");
//        typev.setPic("");
//        typev.setVideos("");
//        Typev typev1=new Typev();
//        typev.setName("");
//        typev.setPic("");
//        typev.setVideos("");
//        Typev typev1=new Typev();
//        typev.setName("");
//        typev.setPic("");
//        typev.setVideos("");
//        Typev typev1=new Typev();
//        typev.setName("");
//        typev.setPic("");
//        typev.setVideos("");
//        Typev typev1=new Typev();
//        typev.setName("");
//        typev.setPic("");
//        typev.setVideos("");
//        Typev typev1=new Typev();
//        typev.setName("");
//        typev.setPic("");
//        typev.setVideos("");
//        abc.add(typev);
//        abc.add(typev1);
//        abc.add(typev3);
//        abc.add(typev4);
//        abc.add("https://vd2.bdstatic.com/mda-jm0ic6tupg8gfzhk/mda-jm0ic6tupg8gfzhk.mp4?playlist=%5B%22hd%22%2C%22sc%22%5D&auth_key=1575969963-0-0-7a7b525ac1e2c338aa41ffabf68b1631&bcevod_channel=searchbox_feed");
//        abc.add("https://vd3.bdstatic.com/mda-if3qgw10e4iq72fv/logo/mda-if3qgw10e4iq72fv.mp4?playlist=%5B%22hd%22%2C%22sc%22%5D&auth_key=1575971904-0-0-5ef2d9efd45354da240aad85af8984e1&bcevod_channel=searchbox_feed");
//        abc.add("https://vd3.bdstatic.com/mda-jk1qfhmhkityuan4/mda-jk1qfhmhkityuan4.mp4?playlist=%5B%22hd%22%2C%22sc%22%5D&auth_key=1575971846-0-0-1577e3f0f27a832a695da6c0247c5751&bcevod_channel=searchbox_feed");
//        abc.add("https://vd4.bdstatic.com/mda-ifudqrpf7pnba4t5/logo/mda-ifudqrpf7pnba4t5.mp4?playlist=%5B%22hd%22%2C%22sc%22%5D&auth_key=1575971941-0-0-a61bc161fbc541ddc6b9bd3383e83384&bcevod_channel=searchbox_feed");
//        abc.add("https://vd3.bdstatic.com/mda-jjbpcqn5saswv31f/mda-jjbpcqn5saswv31f.mp4?playlist=%5B%22hd%22%2C%22sc%22%5D&auth_key=1575972064-0-0-c65bbcdb7d95f1d3aef65e15f080e093&bcevod_channel=searchbox_feed");
        initViewPager();
        ButterKnife.bind(this.getActivity());
        detector = new GestureDetector(this.getActivity(), this);
        return view;
    }





    private void initViewPager() {
        currentFlagPostion =getActivity().getIntent().getIntExtra("currentPostion", 0);

        int i=0;
        while(i<ggg.size()){

            Video video = new Video();
            video.setTitle(def.get(i));
            video.setVideoUrl(ggg.get(i));
            video.setImageUrl(cde.get(i));
            list.add(video);
            i++;
        }
        myPagerAdapter = new MyPagerAdapter();
        vp.setAdapter(myPagerAdapter);
        vp.setOffscreenPageLimit(3);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int postition) {
                mCurrentPosition = postition;
                // 滑动界面，首先让之前的播放器暂停，并seek到0
                if (aliPlayer != null) {
                    tabWidget.setVisibility(View.GONE);
                    aliPlayer.seekTo(0);
                    aliPlayer.pause();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        vp.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                if (position != 0) {
                    return;
                }
                PlayerInfo playerInfo = myPagerAdapter.findPlayerInfo(mCurrentPosition);
                if (playerInfo != null) {
                    if (playerInfo.getAliPlayer() != null) {
                        playerInfo.getAliPlayer().start();
                        aliPlayer = playerInfo.getAliPlayer();
                    }
                }
            }
        });
        vp.setCurrentItem(currentFlagPostion);
    }






    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Toast.makeText(
                getContext(),
                "账户或密码错误",
                Toast.LENGTH_SHORT);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }


    class MyPagerAdapter extends PagerAdapter {

        ArrayList<PlayerInfo> playerInfoList = new ArrayList<>();
        private LinkedList<View> mViewCache = new LinkedList<>();

        protected PlayerInfo instantiatePlayerInfo(int position) {
            AliPlayer aliyunVodPlayer = AliPlayerFactory.createAliPlayer(getContext());
            PlayerInfo playerInfo = new PlayerInfo();
            playerInfo.setPlayURL(list.get(position).getVideoUrl());
            playerInfo.setAliPlayer(aliyunVodPlayer);
            playerInfo.setPosition(position);
            playerInfoList.add(playerInfo);
            return playerInfo;
        }

        public PlayerInfo findPlayerInfo(int position) {
            for (int i = 0; i < playerInfoList.size(); i++) {
                PlayerInfo playerInfo = playerInfoList.get(i);
                if (playerInfo.getPosition() == position) {
                    return playerInfo;
                }
            }
            return null;
        }

        public void mOnDestroy() {
            for (PlayerInfo playerInfo : playerInfoList) {
                if (playerInfo.getAliPlayer() != null) {
                    playerInfo.getAliPlayer().release();
                }
            }
            playerInfoList.clear();
        }

        protected void destroyPlayerInfo(int position) {
            while (true) {
                PlayerInfo playerInfo = findPlayerInfo(position);
                if (playerInfo == null)
                    break;
                if (playerInfo.getAliPlayer() == null)
                    break;
                playerInfo.getAliPlayer().release();
                playerInfoList.remove(playerInfo);
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            View view;
            if (mViewCache.size() == 0) {
                view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_main_viewpage, null, false);
            } else {
                view = mViewCache.removeFirst();
            }
            view.setId(position);
            TextView videoTitle = view.findViewById(R.id.item_main_video_title);
            final ImageView coverPicture = view.findViewById(R.id.item_main_cover_picture);
            SurfaceView surfaceView = view.findViewById(R.id.item_main_surface_view);
            surfaceView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tabWidget.setVisibility(VISIBLE);

                }

            });
            surfaceView.setZOrderOnTop(true);


            if (!TextUtils.isEmpty(list.get(position).getImageUrl())) {
                coverPicture.setVisibility(VISIBLE);
                Glide.with(ThirdFragment.this).load(list.get(position).getImageUrl()).into(coverPicture);
            }
            videoTitle.setText("<  " + list.get(position).getTitle());
            videoTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tabWidget.setVisibility(VISIBLE);

                }

            });


            final PlayerInfo playerInfo = instantiatePlayerInfo(position);
            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    playerInfo.getAliPlayer().setDisplay(holder);
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    playerInfo.getAliPlayer().redraw();
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    playerInfo.getAliPlayer().setDisplay(null);
                }
            });
            UrlSource urlSource = new UrlSource();
            urlSource.setUri(list.get(position).getVideoUrl());
            //设置播放源
            playerInfo.getAliPlayer().setDataSource(urlSource);
            //准备播放
            playerInfo.getAliPlayer().prepare();

            //开启缓存
            CacheConfig cacheConfig = new CacheConfig();
            //开启缓存功能
            cacheConfig.mEnable = true;
            //能够缓存的单个文件最大时长。超过此长度则不缓存
            cacheConfig.mMaxDurationS = 300;
            //缓存目录的位置
            cacheConfig.mDir = "hbw";
            //缓存目录的最大大小。超过此大小，将会删除最旧的缓存文件
            cacheConfig.mMaxSizeMB = 200;
            //设置缓存配置给到播放器
            playerInfo.getAliPlayer().setCacheConfig(cacheConfig);

            playerInfo.getAliPlayer().setLoop(true);
            playerInfo.getAliPlayer().setOnPreparedListener(new IPlayer.OnPreparedListener() {
                @Override
                public void onPrepared() {
                    // 视频准备成功之后影响封面图
                    if (!TextUtils.isEmpty(list.get(position).getImageUrl())) {
                        coverPicture.setVisibility(LinearLayout.GONE);
                    }
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            destroyPlayerInfo(position);
            View contentView = (View) object;
            container.removeView(contentView);
            mViewCache.add(contentView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (aliPlayer != null) {
            aliPlayer.release();
        }
        if (myPagerAdapter != null) {
            myPagerAdapter.mOnDestroy();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (aliPlayer != null) {
            aliPlayer.pause();
        }
    }

    @Override
    public void onResume() {
//        CountDownTimer startTime = new CountDownTimer(500,500) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//            }
//            @Override
//            public void onFinish() {
//                Random random = new Random();
//                aliPlayer.setData(random.nextInt(130-90+1)+90);
//            }
//        }.start();
        super.onResume();


    }
    private void showName(){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.43.226:8080/OurProject/ShowVideosServlet?food=番茄炒蛋");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"GB2312"));
                    String info1 = "";
                    while((info1 = reader.readLine()) != null){
                        Message message = Message.obtain();
                        message.obj = info1.toString();
                        message.what = 100;
                        handler.sendMessage(message);
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void clearFragments(){
        FragmentManager fm = getChildFragmentManager();
        if(fm != null && fm.getFragments() != null && fm.getFragments().size() > 0){
            FragmentTransaction ft = fm.beginTransaction();
            for(Fragment fragment : fm.getFragments()){
                ft.remove(fragment);
            }
            ft.commit();
        }
    }

    private void wrapMessage(String info){
        Message message = Message.obtain();
        message.what = 100;
        message.obj = info;
        handler.sendMessage(message);
    }
    private void showVideo(){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.2.162:8080/canMouZhangSignTest/ShowVideosServlet");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = "";
                    while((info=reader.readLine()) != null){
                        wrapMessage(info);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}
