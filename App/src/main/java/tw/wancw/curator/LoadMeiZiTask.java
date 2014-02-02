package tw.wancw.curator;

import android.os.AsyncTask;

import java.util.Arrays;

import tw.wancw.curator.widget.MeiZiCardAdapter;


public class LoadMeiZiTask extends AsyncTask<String, Void, MeiZiCard[]> {

    private final MeiZiCardAdapter adapter;

    public LoadMeiZiTask(MeiZiCardAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected MeiZiCard[] doInBackground(String... strings) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        return new MeiZiCard[] {
            new MeiZiCard("小悠 / Judy", "http://t.curator.im/9sTJDaUMqqnWG6acMajyP-R1OGE=/500x0/media.curator.im/images/132434493631215/171637146377616_1460298_171637146377616_834083471_n.jpg"),
            new MeiZiCard("Shakira Chung", "http://t.curator.im/TSy40R1QIu_0RsLgYOq9Nd0u210=/500x0/media.curator.im/images/1408849209352845/1422670297970736_1502594_1422670297970736_932427694_o.jpg"),
            new MeiZiCard("Shakira Chung", "http://t.curator.im/HDkT9EgmuLdvvlzuB1FLZrrqgHk=/500x0/media.curator.im/images/1408849209352845/1422670211304078_1412314_1422670211304078_481140092_o.jpg"),
            new MeiZiCard("Shakira Chung", "http://t.curator.im/doE9zSqwqybuGR__zN67i0Xr46o=/500x0/media.curator.im/images/1408849209352845/1422567154647717_1538798_1422567154647717_525836477_n.jpg"),
        };
    }

    @Override
    protected void onPostExecute(MeiZiCard[] cards) {
        adapter.add(Arrays.asList(cards));
    }
}
