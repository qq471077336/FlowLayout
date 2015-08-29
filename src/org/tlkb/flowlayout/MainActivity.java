package org.tlkb.flowlayout;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

public class MainActivity extends Activity {

	private FlowLayout mFlow;

	private String[] mDatas = new String[] { "海军上将", "兽王", "半人马酋长", "撼地神牛", "全能骑士", "熊猫酒仙", "流浪剑客", "山岭巨人", "树精卫士", "牛头人酋长", "精灵守卫", "炼金术士", "发条地精",
			"龙骑士", "神灵武士", "刚被兽", "凤凰", "巨牙海民", "军团指挥官", "地精撕裂者", "斧王", "混沌骑士", "末日使者", "食尸鬼", "地狱领主", "狼人", "暗夜魔王", "深渊领主", "屠夫", "骷髅王", "鱼人守卫",
			"不朽尸王", "潮汐猎人", "半人猛犸", "裂魂人", "沙王", "敌法师", "矮人狙击手", "剑圣", "德鲁伊", "月之骑士", "变体精灵", "娜迦海妖", "幻影长矛手", "月之女祭祀", "隐形刺客", "巨魔战将", "矮人直升机",
			"黑暗游侠", "圣堂刺客", "熊战士", "复仇之魂", "赏金猎人", "灰烬之灵", "血魔", "骷髅射手", "育母蜘蛛", "地穴刺客", "地穴编织者", "幻影刺客", "影魔", "灵魂守卫", "幽鬼", "剧毒术士", "冥界亚龙", "地卜师",
			"闪电幽魂", "蛇发女妖", "鱼人夜行者", "虚空假面", "水晶室女", "魅惑魔女", "仙女龙", "圣骑士", "光之守卫", "众神之王", "先知", "沉默术士", "秀逗魔法师", "风暴之灵", "风行者", "预言者", "食人魔魔法师",
			"哥布林工程师", "双头龙", "地精修补匠", "暗影萨满", "大魔导师", "天怒法师", "创世者", "痛苦之源", "黑暗贤者", "死亡先知", "恶魔巫师", "谜团", "巫妖", "死灵法师", "遗忘法师", "黑曜毁灭者", "痛苦女王",
			"术士", "暗影恶魔", "蝙蝠骑士", "暗影牧师", "死灵飞龙", "受折磨的灵魂", "巫医", "极寒幽魂" };

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mFlow = (FlowLayout) findViewById(R.id.flow);
		mFlow.setPadding(10, 10, 10, 10);

		// 加载数据

		Random rdm = new Random();
		// 显示数据
		for (int i = 0; i < mDatas.length; i++) {

			TextView view = new TextView(this);
			view.setText(mDatas[i]);
			view.setBackgroundColor(Color.GRAY);
			view.setTextColor(Color.WHITE);
			view.setPadding(5, 5, 5, 5);
			view.setGravity(Gravity.CENTER);
			view.setTextSize(rdm.nextInt(16) + 10);
			// view.setTextSize(14);

			GradientDrawable normal = new GradientDrawable();
			normal.setShape(GradientDrawable.RECTANGLE);
			normal.setCornerRadius(10);
			Random r = new Random();
			int red = r.nextInt(200) + 20;
			int green = r.nextInt(200) + 20;
			int blue = r.nextInt(200) + 20;
			int argb = Color.argb(255, red, green, blue);
			normal.setColor(argb);

			GradientDrawable pressed = new GradientDrawable();
			pressed.setShape(GradientDrawable.RECTANGLE);
			pressed.setCornerRadius(10);
			pressed.setColor(Color.GRAY);

			StateListDrawable selector = new StateListDrawable();
			selector.addState(new int[] { android.R.attr.state_pressed }, pressed);
			selector.addState(new int[] {}, normal);

			view.setBackgroundDrawable(selector);

			mFlow.addView(view);
		}
	}

}
