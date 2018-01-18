package kore.botssdk.view.viewUtils;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import kore.botssdk.R;
import kore.botssdk.adapter.BotCarouselItemButtonAdapter;
import kore.botssdk.application.AppControl;
import kore.botssdk.fragment.ComposeFooterFragment;
import kore.botssdk.listener.InvokeGenericWebViewInterface;
import kore.botssdk.models.BotCaourselButtonModel;
import kore.botssdk.models.BotCarouselModel;
import kore.botssdk.utils.BundleConstants;
import kore.botssdk.utils.StringUtils;
import kore.botssdk.utils.Utils;

/**
 * Created by Pradeep Mahato on 19/7/17.
 * Copyright (c) 2014 Kore Inc. All rights reserved.
 */
public class CarouselItemViewHelper {

    public static class CarouselViewHolder {
        ImageView carouselItemImage;
        TextView carouselItemTitle, carouselItemSubTitle;
        ListView carouselButtonListview;
        CardView carouselItemRoot;

        FrameLayout carouselOfferPrice_FL,carouselSavedPrice_FL;
        TextView carousel_item_offer,carousel_item_save_price;
    }

    public static void initializeViewHolder(View view) {
        CarouselViewHolder carouselViewHolder = new CarouselViewHolder();

        carouselViewHolder.carouselItemRoot = (CardView) view.findViewById(R.id.carousel_item_root);
        carouselViewHolder.carouselItemImage = (ImageView) view.findViewById(R.id.carousel_item_image);
        carouselViewHolder.carouselItemTitle = (TextView) view.findViewById(R.id.carousel_item_title);
        carouselViewHolder.carouselItemSubTitle = (TextView) view.findViewById(R.id.carousel_item_subtitle);
        carouselViewHolder.carouselButtonListview = (ListView) view.findViewById(R.id.carousel_button_listview);

        carouselViewHolder.carouselOfferPrice_FL =  (FrameLayout) view.findViewById(R.id.offer_price_fl);
        carouselViewHolder.carouselSavedPrice_FL =  (FrameLayout) view.findViewById(R.id.saved_price_fl);

        carouselViewHolder.carousel_item_offer = (TextView) view.findViewById(R.id.carousel_item_offer);
        carouselViewHolder.carousel_item_save_price= (TextView) view.findViewById(R.id.carousel_item_saved);

        view.setTag(carouselViewHolder);
    }

    public static void populateStuffs(CarouselViewHolder carouselViewHolder,
                                      final ComposeFooterFragment.ComposeFooterInterface composeFooterInterface,
                                      final InvokeGenericWebViewInterface invokeGenericWebViewInterface,
                                      final BotCarouselModel botCarouselModel,
                                      Context activityContext) {

        if (botCarouselModel != null) {

            float dp1 = AppControl.getInstance().getDimensionUtil().dp1;

            carouselViewHolder.carouselItemTitle.setText(botCarouselModel.getTitle());
            carouselViewHolder.carouselItemSubTitle.setText(botCarouselModel.getSubtitle());

            Picasso.with(activityContext).load(botCarouselModel.getImage_url()).into(carouselViewHolder.carouselItemImage);

            String price = Utils.isNullOrEmpty(botCarouselModel.getPrice())?"":botCarouselModel.getPrice();
            String cost_price = Utils.isNullOrEmpty(botCarouselModel.getCost_price())?"":botCarouselModel.getCost_price();

            String text = (price+" "+ cost_price).trim();

            SpannableStringBuilder ssBuilder = new SpannableStringBuilder(text);

            // Initialize a new StrikeThroughSpan to display strike through text
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();

            // Apply the strike through text to the span
            ssBuilder.setSpan(
                    strikethroughSpan, // Span to add
                    text.indexOf(price), // Start of the span (inclusive)
                    text.indexOf(price) + String.valueOf(price).length(), // End of the span (exclusive)
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE // Do not extend the span when text add later
            );

            if(!Utils.isNullOrEmpty(botCarouselModel.getPrice()) || !Utils.isNullOrEmpty(botCarouselModel.getCost_price())) {
                carouselViewHolder.carouselOfferPrice_FL.setVisibility(View.VISIBLE);
                if(!Utils.isNullOrEmpty(botCarouselModel.getCost_price()))
                    carouselViewHolder.carousel_item_offer.setText(ssBuilder);
                else
                    carouselViewHolder.carousel_item_offer.setText(text);
            }else{
                carouselViewHolder.carouselOfferPrice_FL.setVisibility(View.GONE);
            }

            if(!Utils.isNullOrEmpty(botCarouselModel.getSaved_price())) {
                carouselViewHolder.carouselSavedPrice_FL.setVisibility(View.VISIBLE);
                carouselViewHolder.carousel_item_save_price.setText(botCarouselModel.getSaved_price());
            }else{
                carouselViewHolder.carouselSavedPrice_FL.setVisibility(View.GONE);
            }

            BotCarouselItemButtonAdapter botCarouselItemButtonAdapter = new BotCarouselItemButtonAdapter(activityContext);
            carouselViewHolder.carouselButtonListview.setAdapter(botCarouselItemButtonAdapter);
            botCarouselItemButtonAdapter.setBotCaourselButtonModels(botCarouselModel.getButtons());
            carouselViewHolder.carouselButtonListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (composeFooterInterface != null && invokeGenericWebViewInterface != null) {
                        BotCaourselButtonModel botCaourselButtonModel = (BotCaourselButtonModel) parent.getAdapter().getItem(position);
                        if (BundleConstants.BUTTON_TYPE_WEB_URL.equalsIgnoreCase(botCaourselButtonModel.getType())) {
                            invokeGenericWebViewInterface.invokeGenericWebView(botCaourselButtonModel.getUrl());
                        } else if (BundleConstants.BUTTON_TYPE_POSTBACK.equalsIgnoreCase(botCaourselButtonModel.getType())) {
                            String buttonPayload = botCaourselButtonModel.getPayload();
                            String buttonTitle = botCaourselButtonModel.getTitle();
                            composeFooterInterface.onSendClick(buttonTitle, buttonPayload);
                        }
                    }
                }
            });

            carouselViewHolder.carouselItemRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (invokeGenericWebViewInterface != null) {
                        if (botCarouselModel.getDefault_action() != null &&
                                BundleConstants.BUTTON_TYPE_WEB_URL.equalsIgnoreCase(botCarouselModel.getDefault_action().getType())) {
                            invokeGenericWebViewInterface.invokeGenericWebView(botCarouselModel.getDefault_action().getUrl());
                        } else if (botCarouselModel.getDefault_action() != null &&
                                BundleConstants.BUTTON_TYPE_POSTBACK.equalsIgnoreCase(botCarouselModel.getDefault_action().getType())) {
                            String buttonPayload = botCarouselModel.getDefault_action().getPayload();
                            composeFooterInterface.onSendClick(buttonPayload);
                        }
                    }
                }
            });
        }
    }

    private static int getButtonHeight(Context context, int itemCount, float dp1) {
        return (int) (context.getResources().getDimension(R.dimen.carousel_view_button_height_individual) * dp1 + 4 * dp1);
    }
}
