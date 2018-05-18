package kore.botssdk.view.viewUtils;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;

import kore.botssdk.R;
import kore.botssdk.adapter.BotCarouselItemButtonAdapter;
import kore.botssdk.application.AppControl;
import kore.botssdk.fragment.ComposeFooterFragment;
import kore.botssdk.listener.InvokeGenericWebViewInterface;
import kore.botssdk.models.BotCaourselButtonModel;
import kore.botssdk.models.BotCarouselModel;
import kore.botssdk.models.BotListDefaultModel;
import kore.botssdk.models.KnowledgeDetailModel;
import kore.botssdk.utils.BundleConstants;
import kore.botssdk.utils.Utils;

import static android.view.View.GONE;

/**
 * Created by Pradeep Mahato on 19/7/17.
 * Copyright (c) 2014 Kore Inc. All rights reserved.
 */
public class CarouselItemViewHelper {

    public static class CarouselViewHolder {
        public ImageView carouselItemImage;
        public TextView carouselItemTitle;
        public TextView carouselItemSubTitle;
        public TextView hashTagsView;
        public TextView knowledgeType;
        public TextView knowledgeMode;
        public RelativeLayout koraItems;
        ListView carouselButtonListview;
        public CardView carouselItemRoot;

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

        carouselViewHolder.hashTagsView = (TextView) view.findViewById(R.id.hash_tags_view);
        carouselViewHolder.knowledgeType = (TextView) view.findViewById(R.id.knowledge_type);
        carouselViewHolder.knowledgeMode = (TextView) view.findViewById(R.id.knowledge_mode);
        carouselViewHolder.koraItems = (RelativeLayout) view.findViewById(R.id.kora_items);
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
            carouselViewHolder.carouselItemSubTitle.setText(Html.fromHtml(StringEscapeUtils.unescapeHtml4(botCarouselModel.getSubtitle().replaceAll("<br>"," "))));

            try {
                if(botCarouselModel.getImage_url() != null && !botCarouselModel.getImage_url().isEmpty())
                    Picasso.with(activityContext).load(botCarouselModel.getImage_url()).into(carouselViewHolder.carouselItemImage);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(botCarouselModel.getButtons() != null) {
                BotCarouselItemButtonAdapter botCarouselItemButtonAdapter = new BotCarouselItemButtonAdapter(activityContext);
                carouselViewHolder.carouselButtonListview.setAdapter(botCarouselItemButtonAdapter);
                botCarouselItemButtonAdapter.setBotCaourselButtonModels(botCarouselModel.getButtons());
            }

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
           // carouselViewHolder.carouselButtonListview.getLayoutParams().height = (int)(botCarouselModel.getButtons() != null ? botCarouselModel.getButtons().size() * (48 * dp1) : 0);
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
                        }else if(BundleConstants.BUTTON_TYPE_POSTBACK_DISP_PAYLOAD.equalsIgnoreCase(botCaourselButtonModel.getType())){
                            String buttonPayload = botCaourselButtonModel.getPayload();
//                            String buttonTitle = botCaourselButtonModel.getTitle();
                            composeFooterInterface.onSendClick(buttonPayload, buttonPayload);
                        }else if (BundleConstants.BUTTON_TYPE_USER_INTENT.equalsIgnoreCase(botCaourselButtonModel.getType())) {
                            invokeGenericWebViewInterface.handleUserActions(botCaourselButtonModel.getAction(),botCaourselButtonModel.getCustomData());
                        }
                    }
                }
            });

            carouselViewHolder.carouselItemRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BotListDefaultModel botListDefaultModel = botCarouselModel.getDefault_action();
                    if (invokeGenericWebViewInterface != null && botListDefaultModel != null) {
                        if (BundleConstants.BUTTON_TYPE_WEB_URL.equalsIgnoreCase(botListDefaultModel.getType())) {
                            invokeGenericWebViewInterface.invokeGenericWebView(botListDefaultModel.getUrl());
                        } else if (BundleConstants.BUTTON_TYPE_USER_INTENT.equalsIgnoreCase(botListDefaultModel.getType())) {
                            invokeGenericWebViewInterface.handleUserActions(botListDefaultModel.getAction(), botListDefaultModel.getCustomData());
                        }
                    } else if (composeFooterInterface != null && botListDefaultModel != null) {
                        if (BundleConstants.BUTTON_TYPE_POSTBACK.equalsIgnoreCase(botListDefaultModel.getType())) {
                            String buttonPayload = botCarouselModel.getDefault_action().getPayload();
                            composeFooterInterface.onSendClick(buttonPayload);
                        } else if (BundleConstants.BUTTON_TYPE_POSTBACK_DISP_PAYLOAD.equalsIgnoreCase(botListDefaultModel.getType())) {
                            String buttonPayload = botCarouselModel.getDefault_action().getPayload();
                            composeFooterInterface.onSendClick(buttonPayload);
                        }
                    }
                }
            });
            if(botCarouselModel instanceof KnowledgeDetailModel){

                if (org.apache.commons.lang3.StringUtils.isEmpty(botCarouselModel.getImage_url())) {
                    carouselViewHolder.carouselItemImage.setVisibility(GONE);
                } else {
                    carouselViewHolder.carouselItemImage.setVisibility(View.VISIBLE);
                    carouselViewHolder.carouselItemImage.setScaleType(ImageView.ScaleType.FIT_XY);
                }


                if (org.apache.commons.lang3.StringUtils.isEmpty(botCarouselModel.getTitle())) {
                    carouselViewHolder.carouselItemTitle.setVisibility(GONE);
                } else {
                    carouselViewHolder.carouselItemTitle.setVisibility(View.VISIBLE);
                }

                if (org.apache.commons.lang3.StringUtils.isEmpty(botCarouselModel.getSubtitle())) {
                    carouselViewHolder.carouselItemSubTitle.setVisibility(GONE);
                } else {
                    carouselViewHolder.carouselItemSubTitle.setVisibility(View.VISIBLE);
                }

                carouselViewHolder.carouselItemTitle.setGravity(Gravity.LEFT);
                carouselViewHolder.carouselItemSubTitle.setGravity(Gravity.LEFT);
                carouselViewHolder.koraItems.setVisibility(View.VISIBLE);
                ArrayList<String> hashTags = ((KnowledgeDetailModel)botCarouselModel).getHashTag();
                StringBuilder hashText = new StringBuilder();
                if(hashTags != null && hashTags.size()> 0) {
                    for (String tag : hashTags) {
                        if(!tag.trim().isEmpty())
                            hashText.append("  #").append(tag);
                        if(hashText.length()>3) {
                            carouselViewHolder.hashTagsView.setText(hashText.substring(2));
                            carouselViewHolder.hashTagsView.setVisibility(View.VISIBLE);
                        }else{
                            carouselViewHolder.hashTagsView.setVisibility(View.GONE);
                        }
                    }
                }else{
                    carouselViewHolder.hashTagsView.setVisibility(GONE);
                }
                carouselViewHolder.knowledgeType.setText("Link: News Article");
                carouselViewHolder.knowledgeMode.setText(((KnowledgeDetailModel) botCarouselModel).getSharesCount()== 0 ? "Private" : "Shared");
            }else{
                carouselViewHolder.koraItems.setVisibility(View.GONE);
            }

        }
    }

    private static int getButtonHeight(Context context, int itemCount, float dp1) {
        return (int) (context.getResources().getDimension(R.dimen.carousel_view_button_height_individual) * dp1 + 4 * dp1);
    }


}
