package com.onceas.wm.console.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.util.Date;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Minute;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class WorkManagerChartDrawer extends MBeanServerWrapper {

	private static ObjectName objName;

	static {
		try {
			objName = new javax.management.ObjectName(
					"onceas.work.runtime:type=ThreadPool,ThreadPoolRuntime=OnceASThreadPoolRuntime");
		} catch (MalformedObjectNameException e) {
			throw new RuntimeException(e);
		} catch (NullPointerException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @return the chart
	 */
	@SuppressWarnings("unused")
	public static JFreeChart draw() {

		if (!ThreadPoolStatistic.startFlag) {
			ThreadPoolStatistic.throughPut.clear();
			ThreadPoolStatistic.startSample((Integer) getAttribute(objName,"ExecuteThreadCount"));
		} else {
			ThreadPoolStatistic.setThroughPut((Integer)getAttribute(objName,"ExecuteThreadCount"));
		}

		// 绘图部分
		
		String graphURL = "";

		String title = "线程池";
		// X轴
		String domain = "时间";
		// Y轴
		String range = "线程使用";

		String subtitleStr = "近一小时使用情况";

		RegularTimePeriod start = new Minute();

		TimeSeries memory = new TimeSeries("活动线程数", Second.class);

		String filename = null;

		// RegularTimePeriod period = start;
		Date sampleStartTime = ThreadPoolStatistic.threadPoolSampleStartTime;

		for (int j = 0; j < ThreadPoolStatistic.throughPut.size(); j++) {
			// memory.add( new Second(sampleStartTime), new
			// Double(70+Math.random()*50));

			memory.add(new Second(sampleStartTime), new Double(
					(Integer) (ThreadPoolStatistic.throughPut.get(j))));

			sampleStartTime = new Date(sampleStartTime.getTime()
					+ ThreadPoolStatistic.threadPoolSampleTimeInterval * 1000L);
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();

		dataset.addSeries(memory);
		// System.out.println(sta.maxcount);
		// System.out.println(sta.getMaxUsed());

		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, domain,
				range, dataset, true, true, false);

		   TextTitle textTitle = chart.getTitle();
		   textTitle.setFont(new Font("宋体", Font.BOLD, 16));
//		   domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
//		   domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
//		   numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
//		   numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));
		   chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
		
		
		TextTitle subtitle = new TextTitle(subtitleStr, new Font("宋体",
				Font.BOLD, 12));
		subtitle.setFont(new Font("宋体",Font.BOLD, 12));
		
		chart.addSubtitle(subtitle);

		chart.setTitle(new TextTitle(title, new Font("宋体", Font.BOLD, 16)));

		XYPlot plot = chart.getXYPlot();
		// 设置纵坐标对小单位为整数
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
		rangeAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
		//X轴
		ValueAxis domainAxis =	plot.getDomainAxis();
		domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
		
		chart.setBackgroundPaint(new GradientPaint(0, 0, Color.white, 0, 1000,
				new Color(179, 214, 249)));

		return chart;
	}
	
	
	@SuppressWarnings("unused")
	public static JFreeChart drawThroughput() {

		if (!ThroughputStatistic.startFlag) {
			ThroughputStatistic.throughPut.clear();
			ThroughputStatistic.startSample(((Double)getAttribute(objName, "Throughput")).longValue());
		} else {
			ThroughputStatistic.setThroughPut(((Double)getAttribute(objName, "Throughput")).longValue());
		}

		// 绘图部分
		
		String graphURL = "";

		String title = "吞吐量";
		// X轴
		String domain = "时间";
		// Y轴
		String range = "吞吐量情况";

		String subtitleStr = "近一小时使用情况";

		RegularTimePeriod start = new Minute();

		TimeSeries memory = new TimeSeries("吞吐量", Second.class);

		String filename = null;

		// RegularTimePeriod period = start;
		Date sampleStartTime = ThroughputStatistic.throughputSampleStartTime;

		for (int j = 0; j < ThroughputStatistic.throughPut.size(); j++) {
			// memory.add( new Second(sampleStartTime), new
			// Double(70+Math.random()*50));

			memory.add(new Second(sampleStartTime), new Double(
					(Long) (ThroughputStatistic.throughPut.get(j))));

			sampleStartTime = new Date(sampleStartTime.getTime()
					+ ThroughputStatistic.throughputSampleTimeInterval * 1000L);
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();

		dataset.addSeries(memory);
		// System.out.println(sta.maxcount);
		// System.out.println(sta.getMaxUsed());

		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, domain,
				range, dataset, true, true, false);

		   TextTitle textTitle = chart.getTitle();
		   textTitle.setFont(new Font("宋体", Font.BOLD, 16));
//		   domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
//		   domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
//		   numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
//		   numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));
		   chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
		
		
		TextTitle subtitle = new TextTitle(subtitleStr, new Font("宋体",
				Font.BOLD, 12));
		subtitle.setFont(new Font("宋体",Font.BOLD, 12));
		
		chart.addSubtitle(subtitle);

		chart.setTitle(new TextTitle(title, new Font("宋体", Font.BOLD, 16)));

		XYPlot plot = chart.getXYPlot();
		// 设置纵坐标对小单位为整数
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
		rangeAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
		//X轴
		ValueAxis domainAxis =	plot.getDomainAxis();
		domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
		
		chart.setBackgroundPaint(new GradientPaint(0, 0, Color.white, 0, 1000,
				new Color(179, 214, 249)));

		return chart;
	}
}
