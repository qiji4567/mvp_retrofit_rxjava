package com.htbot.coffee.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.csnprintersdk.csnio.CSNPOS;
import com.htbot.coffee.R;
import com.htbot.coffee.entity.GoodsBean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Prints {
    public static int PrintTicket(Context mContext,
                                  CSNPOS pos, int nPrintWidth, boolean bCutter,
                                  boolean bDrawer, boolean bBeeper, int nCount,
                                  int nPrintContent, int nCompressMethod, List<GoodsBean> goodsBeans,
                                  String allMoney, String payType, String orderTime, String orderNumber, String orderWaitId) {
        int bPrintResult = 0;
        Bitmap bmYellowmen = getImageFromAssetsFile(mContext, "ic_logo.png");

        byte[] status = new byte[1];
        if (pos.POS_RTQueryStatus(status, 3, 1000, 2)) {

            if ((status[0] & 0x08) == 0x08)   //判断切刀是否异常
                return bPrintResult = -2;

            if ((status[0] & 0x40) == 0x40)   //判断打印头是否在正常值范围内
                return bPrintResult = -3;

            if (pos.POS_RTQueryStatus(status, 2, 1000, 2)) {

                if ((status[0] & 0x04) == 0x04)    //判断合盖是否正常
                    return bPrintResult = -6;
                if ((status[0] & 0x20) == 0x20)    //判断是否缺纸
                    return bPrintResult = -5;
                else {
                    Bitmap bm1 = getTestImage1(nPrintWidth, nPrintWidth);
                    Bitmap bm2 = getTestImage2(nPrintWidth, nPrintWidth);
                    Bitmap bmBlackWhite = getImageFromAssetsFile(mContext, "blackwhite.png");
                    Bitmap bmIu = getImageFromAssetsFile(mContext, "iu.jpeg");

//					Bitmap bm01 = getImageFromAssetsFile(mContext, "01.jpg");
//					Bitmap bm02 = getImageFromAssetsFile(mContext, "02.jpg");
//					Bitmap bm03 = getImageFromAssetsFile(mContext, "03.jpg");
//					Bitmap bm04 = getImageFromAssetsFile(mContext, "04.jpg");
//					Bitmap bm05 = getImageFromAssetsFile(mContext, "05.jpg");
                    for (int i = 0; i < nCount; i++) {
                        if (!pos.GetIO().IsOpened())
                            break;

                        if (nPrintContent >= 1) {
                            if (nPrintWidth == 384) {
                                pos.POS_Reset();
                                pos.POS_FeedLine();
//								pos.POS_TextOut("電子發票證明聯\r\n", 3, 24, 1, 1, 0, 0);
//								pos.POS_TextOut("\r\n", 3, 24, 0, 0, 0, 0);
//								pos.POS_TextOut("105年05-06月\r\n", 3, 48, 1, 1, 0, 0);
//								pos.POS_TextOut("\r\n", 3, 24, 0, 0, 0, 0);
//								pos.POS_TextOut("TW-56321497\r\n", 3, 60, 1, 1, 0, 0);
//								pos.POS_TextOut("2016-05-26 09:43:29\r\n", 3, 0, 0, 0, 0, 0);
//								pos.POS_TextOut("隨機碼：2887   總計：418\r\n", 3, 0, 0, 0, 0, 0);
//
//								pos.POS_TextOut("賣方：12345678                   \r\n", 3, 0, 0, 0, 0, 0);
//								pos.POS_FeedLine();
//								pos.POS_S_SetBarcode("46661366725", 0, 69, 2, 50, 0, 0);
//								pos.POS_FeedLine();
//								pos.POS_DoubleQRCode("乾電池:1:105", 20, 4, 5, "口罩:1:210:牛奶:1:25", 230, 4, 5, 4);
//								pos.POS_FeedLine();
//								pos.POS_FeedLine();

                                //pos.POS_TextOut("REC" + String.format("%03d", i + 1) + "\r\nPrinter\r\n简体中文测试\r\n\r\n", 0, 1, 1, 0, 0, 0);
//								pos.POS_SetCharSetAndCodePage(0,0);
//								pos.POS_TextOut("電子發票證明聯  $$$ \r\n", 3, 0, 0, 0, 0, 0);
//								pos.POS_TextOut("隨機碼：2887   $$$：418\r\n", 3, 0, 0, 0, 0, 0);
                                pos.POS_TextOut("UTF-8 ==> $$$ \r\n", 3, 0, 0, 0, 0, 0);
//								pos.POS_TextOut("隨機碼：2887   $$$：418\r\n", 3, 0, 0, 0, 0, 0);
//								pos.POS_SetCharSetAndCodePage(0,0);
//								pos.POS_S_TextOut("$$$$$$$\r\n", 150, 0, 0, 0, 0);
//								//pos.POS_TextOut("$$$$$$$\r\n", 1, 0, 0, 0, 0, 0);


                                pos.POS_FeedLine();
                                pos.POS_FeedLine();
                            } else {
                                pos.POS_Reset();
                                pos.POS_FeedLine();
                                // start self
                                pos.POS_TextOut(mContext.getString(R.string.print_receipt_title) +"\r\n", 0, 70, 1, 1, 0, 0);
                                pos.POS_FeedLine();
                                pos.POS_TextOut(mContext.getString(R.string.print_pickup_number) + orderWaitId + "\r\n", 0, 96, 1, 1, 0, 0);
                                pos.POS_FeedLine();
//								pos.POS_TextOut("门店名称: " + DeviceInfoUtil.getDeviceInfo().getName() + "\r\n", 0, 0, 0, 0, 0, 0);
//								pos.POS_TextOut("开票日期: 2025-08-13 18:46\r\n", 0, 0, 0, 0, 0, 0);
//								pos.POS_TextOut("订单号: 202508130001", 0, 0, 0, 0, 0, 0); pos.POS_FeedLine();
//								pos.POS_TextOut("消费明细\r\n", 0, 0, 0, 0, 0, 0);
//								pos.POS_TextOut("商品名称 数量 单价 金额\r\n0", 0, 0, 0, 0, 0, 0);
//								pos.POS_TextOut("热美式 1 0.0 0.1\r\n", 0, 0, 0, 0, 0, 0);
//								pos.POS_TextOut("总计\r\n", 0, 0, 0, 0, 0, 0); pos.POS_FeedLine();
//								pos.POS_TextOut("视频安全提示\r\n", 0, 0, 0, 0, 0, 0);

                                // end self

                                //设备名称/Logo/店铺
                                //订单信息（时间、编号）
                                //餐品明细（名称、数量、单价）
                                //合计金额与支付方式
                                //开票二维码
//								pos.POS_TextOut("电子发票\r\n", 0, 96, 1, 1, 0, 0);
//								pos.POS_FeedLine();
                                String title = mContext.getString(R.string.print_store) + DeviceInfoUtil.getDeviceInfo().getOrgName() + "\r\n";
                                pos.POS_TextOut(title, 0, 0, 0, 0, 0, 0);
                                String deviceName = mContext.getString(R.string.print_device_name) + DeviceInfoUtil.getDeviceInfo().getName() + "\r\n";
                                pos.POS_TextOut(deviceName, 0, 0, 0, 0, 0, 0);

                                pos.POS_TextOut("------------------------------------------\r\n", 0, 0, 0, 0, 0, 0);
//                                String dateTime = "   餐品名称        单价  数量       小计\r\n";
//                                pos.POS_TextOut(dateTime, 0, 0, 0, 0, 0, 0);
//                                for (int index = 0; index < goodsBeans.size(); index++) {
//                                    GoodsBean item = goodsBeans.get(index);
//                                    String prize = Math.round(item.getPrice() * item.getGoodCount() * 100.0) / 100.0 + "";
//                                    pos.POS_TextOut(index + 1 + "  " + item.getName() + "        " + item.getPrice() + "   " + item.getGoodCount() + "        " + prize + "\r\n", 0, 0, 0, 0, 0, 0);
//                                }

                                for (int index = 0; index < goodsBeans.size(); index++) {
                                    GoodsBean item = goodsBeans.get(index);
                                    pos.POS_TextOut(mContext.getString(R.string.print_food_name) + item.getName() + "\r\n", 0, 0, 0, 0, 0, 0);
                                    pos.POS_TextOut(mContext.getString(R.string.print_unit_price) + item.getPrice() + "\r\n", 0, 0, 0, 0, 0, 0);
                                    pos.POS_TextOut(mContext.getString(R.string.print_quantity) + item.getGoodCount() + "\r\n", 0, 0, 0, 0, 0, 0);
                                    String prize = Math.round(item.getPrice() * item.getGoodCount() * 100.0) / 100.0 + "";
                                    pos.POS_TextOut(mContext.getString(R.string.print_subtotal) + prize + "\r\n", 0, 0, 0, 0, 0, 0);
                                    // ⭐ 关键：不是最后一个才打印横线
                                    if (index < goodsBeans.size() - 1) {
                                        pos.POS_TextOut("   -------------------\r\n", 0, 0, 0, 0, 0, 0);
                                    }
                                }
//								pos.POS_TextOut("01.9940228004700    3.98   1.181  20080616\r\n", 0, 0, 0, 0, 0, 0);
//								pos.POS_TextOut("   番石榴     小计：4.70   小计： 4.70小计\r\n", 0, 0, 0, 0, 0, 0);
//								pos.POS_TextOut("02.996100800220     6.00   0.376  20080617\r\n", 0, 0, 0, 0, 0, 0);
//								pos.POS_TextOut("   白面条     小计：2.20          4.70小计\r\n", 0, 0, 0, 0, 0, 0);
//								pos.POS_TextOut("03.6921644701204    3.50   1(包)  20080617\r\n", 0, 0, 0, 0, 0, 0);
//								pos.POS_TextOut("   恒源德调味 小计：3.50          3.50小计\r\n", 0, 0, 0, 0, 0, 0);
//								pos.POS_TextOut("04.9940316000602    5.16   0.116  20080617\r\n", 0, 0, 0, 0, 0, 0);
//								pos.POS_TextOut("   生葱       小计：0.60          0.60小计   \r\n", 0, 0, 0, 0, 0, 0);
                                pos.POS_TextOut("------------------------------------------\r\n", 0, 0, 0, 0, 0, 0);
                                pos.POS_TextOut(mContext.getString(R.string.print_total_amount) + allMoney + "\r\n", 0, 0, 0, 0, 0, 0);
                                pos.POS_TextOut(mContext.getString(R.string.print_payment_method) + payType + "\r\n", 0, 0, 0, 0, 0, 0);

                                pos.POS_TextOut(mContext.getString(R.string.print_order_number) + orderNumber + "\r\n", 0, 0, 0, 0, 0, 0);
                                pos.POS_TextOut(mContext.getString(R.string.print_order_time) + orderTime + "\r\n", 0, 0, 0, 0, 0, 0);
                                pos.POS_FeedLine();
                                pos.POS_TextOut(mContext.getString(R.string.print_food_safety_title) + "\r\n", 0, 0, 0, 0, 1, 0);
                                pos.POS_TextOut(mContext.getString(R.string.print_food_safety_1) + "\r\n", 0, 0, 0, 0, 0, 0);
                                pos.POS_TextOut(mContext.getString(R.string.print_food_safety_2) + "\r\n", 0, 0, 0, 0, 0, 0);
                                pos.POS_TextOut(mContext.getString(R.string.print_food_safety_3) + "\r\n", 0, 0, 0, 0, 0, 0);
                                pos.POS_TextOut(mContext.getString(R.string.print_food_safety_4) + "\r\n", 0, 0, 0, 0, 0, 0);
//								pos.POS_PrintPicture(bmYellowmen, nPrintWidth, 0, nCompressMethod);
                                //pos.POS_TextOut("REC" + String.format("%03d", i + 1) + "\r\nPrinter\r\n简体中文测试\r\n\r\n", 0, 1, 1, 0, 0, 0);
                                pos.POS_FeedLine();
                                pos.POS_FeedLine();
                                pos.POS_FeedLine();
                                pos.POS_FeedLine();

                            }

                            if (nPrintContent == 1 && nCount > 1) {
                                pos.POS_HalfCutPaper();
                                try {
                                    Thread.currentThread();
                                    Thread.sleep(4000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

//						if (nPrintContent >= 2) {
//
//							if (bm1 != null) {
//								pos.POS_PrintPicture(bm1, nPrintWidth, 1, nCompressMethod);
//							}
//							if (bm2 != null) {
//								pos.POS_PrintPicture(bm2, nPrintWidth, 1, nCompressMethod);
//							}
//
//							if (nPrintContent == 2 && nCount > 1) {
//								pos.POS_HalfCutPaper();
//								try {
//									Thread.currentThread();
//									Thread.sleep(4500);
//								} catch (InterruptedException e) {
//									e.printStackTrace();
//								}
//							}
//
//							if (nPrintContent == 2 && nCount == 1) {
//								if (bBeeper)
//									pos.POS_Beep(1, 5);
//								if (bCutter)
//									pos.POS_FullCutPaper();
//								if (bDrawer)
//									pos.POS_KickDrawer(0, 100);
//							}
//						}
                    }

//					if (nPrintContent >= 3) {
//						if (bmBlackWhite != null) {
//							//pos.POS_PrintPicture(bmBlackWhite, nPrintWidth, 1, nCompressMethod);
//						}
//						if (bmIu != null) {
//							//pos.POS_PrintPicture(bmIu, nPrintWidth, 0, nCompressMethod);
//						}
//						if (bmYellowmen != null) {
//							//pos.POS_PrintPicture(bmYellowmen, nPrintWidth, 0, nCompressMethod);
//						}
//
//						if (nPrintContent == 3 && nCount > 1) {
//							pos.POS_HalfCutPaper();
//							try {
//								Thread.currentThread();
//								Thread.sleep(6000);
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
//						}
//
//						if (nPrintContent == 3 && nCount == 1) {
//							if (bBeeper)
//								pos.POS_Beep(1, 5);
//							if (bCutter)
//								pos.POS_FullCutPaper();
//							if (bDrawer)
//								pos.POS_KickDrawer(0, 100);
//						}
//					}
                }

                if (bBeeper)
                    pos.POS_Beep(1, 5);
                if (bCutter && nCount == 1)
                    pos.POS_FullCutPaper();
                if (bDrawer)
                    pos.POS_KickDrawer(0, 100);


                if (nCount == 1) {
                    try {
                        Thread.currentThread();
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            return bPrintResult = -8;           //查询失败
        }

        // --- 在所有打印任务完成后，进行最终切纸和蜂鸣 ---

        // 响蜂鸣器
        if (bBeeper) {
            pos.POS_Beep(1, 5);
        }

        // 启动钱箱
        if (bDrawer) {
            pos.POS_KickDrawer(0, 100);
        }

        // 执行全切，确保切纸在最后执行
        if (bCutter) {
            pos.POS_FullCutPaper();
        }

        // 确保有足够时间让切纸命令执行
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return bPrintResult = 0;
    }

    public static String ResultCodeToString(Context mContext, int code) {
        switch (code) {
            case 3:
                return mContext.getString(R.string.print_return_3);

            case 2:
                return mContext.getString(R.string.print_return_2);

            case 1:
                return mContext.getString(R.string.print_return_1);

            case 0:
                return mContext.getString(R.string.print_return_0);

            case -1:
                return mContext.getString(R.string.print_return_n1);

            case -2:
                return mContext.getString(R.string.print_return_n2);

            case -3:
                return mContext.getString(R.string.print_return_n3);

            case -4:
                return mContext.getString(R.string.print_return_n4);

            case -5:
                return mContext.getString(R.string.print_return_n5);

            case -6:
                return mContext.getString(R.string.print_return_n6);

            case -7:
                return mContext.getString(R.string.print_return_n7);

            case -8:
                return mContext.getString(R.string.print_return_n8);

            case -9:
                return mContext.getString(R.string.print_return_n9);

            case -10:
                return mContext.getString(R.string.print_return_n10);

            case -11:
                return mContext.getString(R.string.print_return_n11);

            case -12:
                return mContext.getString(R.string.print_return_n12);

            case -13:
            default:
                return mContext.getString(R.string.print_return_n13);
        }
    }

    /**
     * 从Assets中读取图片
     */
    public static Bitmap getImageFromAssetsFile(Context ctx, String fileName) {
        Bitmap image = null;
        AssetManager am = ctx.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        // load the origial Bitmap
        Bitmap BitmapOrg = bitmap;

        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        // calculate the scale
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the Bitmap
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);

        // make a Drawable from Bitmap to allow to set the Bitmap
        // to the ImageView, ImageButton or what ever
        return resizedBitmap;
    }

    public static Bitmap getTestImage1(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, 4, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, width, 4, paint);

//		paint.setColor(Color.BLACK);
//		for(int i = 0; i < 8; ++i)
//		{
//			for(int x = i; x < width; x += 8)
//			{
//				for(int y = i; y < height; y += 8)
//				{
//					canvas.drawPoint(x, y, paint);
//				}
//			}
//		}
        return bitmap;
    }

    public static Bitmap getTestImage2(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, width, height, paint);

        paint.setColor(Color.BLACK);
        for (int y = 0; y < height; y += 4) {
            for (int x = y % 32; x < width; x += 32) {
                canvas.drawRect(x, y, x + 4, y + 4, paint);
            }
        }
        return bitmap;
    }
}
