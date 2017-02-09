package ua.pp.oped.aromateque;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ua.pp.oped.aromateque.activity.ActivityProductInfo;
import ua.pp.oped.aromateque.model.ShortProduct;
import ua.pp.oped.aromateque.utility.CustomImageLoader;
import ua.pp.oped.aromateque.utility.Utility;

public class AdapterProductList extends RecyclerView.Adapter<AdapterProductList.ViewHolder> {

    public List<ShortProduct> products;
    private Resources resources;
    private int itemLayoutId;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterProductList(List<ShortProduct> products, Context context, int itemLayoutId) {
        this.products = products;
        this.resources = context.getResources();
        this.itemLayoutId = itemLayoutId;
        this.context = context;
    }

    public AdapterProductList(List<ShortProduct> products, Resources resources) {
        this.products = products;
        this.resources = resources;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View v = layoutInflater.inflate(itemLayoutId, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final ShortProduct product = products.get(i);
        viewHolder.brand.setText(product.getBrand());
        viewHolder.name.setText(product.getName());
        viewHolder.typeAndVolume.setText(product.getTypeAndVolume());
        if (product.getOldPrice() != null) {
            viewHolder.oldPrice.setText(String.format(resources.getString(R.string.product_price), product.getOldPrice()));
            viewHolder.oldPrice.setPaintFlags(viewHolder.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            viewHolder.oldPrice.setVisibility(View.GONE);
        }
        viewHolder.price.setText(String.format(resources.getString(R.string.product_price), product.getPrice()));
        CustomImageLoader.getInstance().displayImage(product.getImageUrl(), viewHolder.image);
        viewHolder.toFavorites.setImageDrawable(Utility.compatGetDrawable(context.getResources(), R.drawable.icon_heart));
        //viewHolder.toFavorites.setImageBitmap(IconSheet.getBitmap(128, 64, 45, 41)); //TODO toFavorites mechanic
        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityProductInfo.class);
                intent.putExtra("product_id", product.getId());
                ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(context, R.anim.right_to_center, R.anim.center_to_left);
                context.startActivity(intent, activityOptions.toBundle());
            }
        });
        //TODO switch icon if already in cart
        viewHolder.toCart.setTag(product.getId());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView brand;
        private TextView name;
        private TextView oldPrice;
        private TextView price;
        private ImageButton toFavorites;
        private ImageButton toCart;
        private TextView typeAndVolume;

        ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.product_image);
            brand = (TextView) itemView.findViewById(R.id.product_brand);
            name = (TextView) itemView.findViewById(R.id.product_name);
            typeAndVolume = (TextView) itemView.findViewById(R.id.product_type_and_volume);
            oldPrice = (TextView) itemView.findViewById(R.id.product_old_price);
            price = (TextView) itemView.findViewById(R.id.product_price);
            toFavorites = (ImageButton) itemView.findViewById(R.id.to_favorites);
            toCart = (ImageButton) itemView.findViewById(R.id.toCart);

        }
    }
}