package com.onceas.wm.console.util;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.tdojo.comodel.FisheyeBarModel;
import net.sourceforge.tdojo.comodel.fisheye.PageFisheyeBarModel;

public class DataUtil {

	public static List<FisheyeBarModel> buildFishEyeModel() {
		List<FisheyeBarModel> model = new ArrayList<FisheyeBarModel>();

		FisheyeBarModel item_1 = new PageFisheyeBarModel("index", "context:dojo/dojox/widget/images/icon_browser.png",
				"index");

		FisheyeBarModel item_2 = new PageFisheyeBarModel("index", "context:dojo/dojox/widget/images/icon_calendar.png",
				"index");
		FisheyeBarModel item_3 = new PageFisheyeBarModel("index", "context:dojo/dojox/widget/images/icon_email.png",
				"index");
		FisheyeBarModel item_4 = new PageFisheyeBarModel("index", "context:dojo/dojox/widget/images/icon_texteditor.png",
				"index");
		FisheyeBarModel item_5 = new PageFisheyeBarModel("index",
				"context:dojo/dojox/widget/images/icon_update.png", "index");
		FisheyeBarModel item_6 = new PageFisheyeBarModel("dojo/index",
				"context:dojo/dojox/widget/images/icon_users.png", "dojo/index");

		model.add(item_1);
		model.add(item_2);
		model.add(item_3);
		model.add(item_4);
		model.add(item_5);
		model.add(item_6);

		return model;
	}
}
