package com.mapua.aquajmt.customerapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.mapua.aquajmt.customerapp.R;
import com.mapua.aquajmt.customerapp.api.Api;

/**
 * Created by IPC on 1/9/2018.
 */

public class CustomDialog extends AppCompatActivity {

    public void showTermsAndCondition(Context context, final Api.TermsAndCondition termsAndCondition) {


        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_terms_and_condition);
        dialog.setCancelable(false);

        TextView tv_desc = (TextView) dialog.findViewById(R.id.tv_desc);
        Button bt_accept = (Button) dialog.findViewById(R.id.bt_accept);
        Button bt_cancel = (Button) dialog.findViewById(R.id.bt_cancel);
        WebView wb_desc = (WebView) dialog.findViewById(R.id.wb_desc);

        tv_desc.setText(context.getString(R.string.terms));
        String text;
        text = "<html><body><p align=\"justify\">";
        text+= "Last updated: September 11, 2017<br/><br/>Please read these Terms and Conditions (\"Terms\", \"Terms and Conditions\")\n" +
                "carefully before using the www.aquajmt.com website and related mobile applications (the \"Service\") operated by\n" +
                "AquaJMT (\"us\", \"we\", or \"our\").<br/><br/>Your access to and use of the Service is conditioned on your acceptance of and\n" +
                "compliance with these Terms. These Terms apply to all visitors, users and\n" +
                "others who access or use the Service.<br/><br/>By accessing or using the Service you agree to be bound by these Terms. If you\n" +
                "disagree with any part of the terms then you may not access the Service.<br/><br/>Ordering<br/><br/>All transactions are completed\n" +
                "through Cash on Delivery with participating water refilling stations. Orders cannot be cancelled ten (10) minutes\n" +
                "after an order has been accepted by a vendor.<br/><br/>Accounts<br/><br/>When you create an account with us, you must provide us information that is\n" +
                "accurate, complete, and current at all times. Failure to do so constitutes a\n" +
                "breach of the Terms, which may result in immediate termination of your account\n" +
                "on our Service.<br/><br/>You are responsible for safeguarding the password that you use to access the\n" +
                "Service and for any activities or actions under your password, whether your\n" +
                "password is with our Service or a third-party service.<br/><br/>You agree not to disclose your password to any third party. You must notify us\n" +
                "immediately upon becoming aware of any breach of security or unauthorized use\n" +
                "of your account.<br/><br/>Links To Other Web Sites<br/><br/>Our Service may contain links to third-party web sites or services that are\n" +
                "not owned or controlled by AquaJMT.<br/><br/>AquaJMT has no control over, and assumes no responsibility for, the content,\n" +
                "privacy policies, or practices of any third party web sites or services. You\n" +
                "further acknowledge and agree that AquaJMT shall not be responsible or liable,\n" +
                "directly or indirectly, for any damage or loss caused or alleged to be caused\n" +
                "by or in connection with use of or reliance on any such content, goods or\n" +
                "services available on or through any such web sites or services.<br/><br/>We strongly advise you to read the terms and conditions and privacy policies\n" +
                "of any third-party web sites or services that you visit.<br/><br/>Termination<br/><br/>We may terminate or suspend access to our Service immediately, without prior\n" +
                "notice or liability, for any reason whatsoever, including without limitation\n" +
                "if you breach the Terms.<br/><br/>All provisions of the Terms which by their nature should survive termination\n" +
                "shall survive termination, including, without limitation, ownership\n" +
                "provisions, warranty disclaimers, indemnity and limitations of liability.<br/><br/>We may terminate or suspend your account immediately, without prior notice or\n" +
                "liability, for any reason whatsoever, including without limitation if you\n" +
                "breach the Terms.<br/><br/>Upon termination, your right to use the Service will immediately cease. If you\n" +
                "wish to terminate your account, you may simply discontinue using the Service.<br/><br/>All provisions of the Terms which by their nature should survive termination\n" +
                "shall survive termination, including, without limitation, ownership\n" +
                "provisions, warranty disclaimers, indemnity and limitations of liability.<br/><br/>Governing Law<br/><br/>These Terms shall be governed and construed in accordance with the laws of\n" +
                "Philippines, without regard to its conflict of law provisions.<br/><br/>Our failure to enforce any right or provision of these Terms will not be\n" +
                "considered a waiver of those rights. If any provision of these Terms is held\n" +
                "to be invalid or unenforceable by a court, the remaining provisions of these\n" +
                "Terms will remain in effect. These Terms constitute the entire agreement\n" +
                "between us regarding our Service, and supersede and replace any prior\n" +
                "agreements we might have between us regarding the Service.<br/><br/>Changes<br/><br/>We reserve the right, at our sole discretion, to modify or replace these Terms\n" +
                "at any time. If a revision is material we will try to provide at least 30 days\n" +
                "notice prior to any new terms taking effect. What constitutes a material\n" +
                "change will be determined at our sole discretion.<br/><br/>By continuing to access or use our Service after those revisions become\n" +
                "effective, you agree to be bound by the revised terms. If you do not agree to\n" +
                "the new terms, please stop using the Service.<br/><br/>Contact Us<br/><br/>If you have any questions about these Terms, please contact us.";
        text+= "</p></body></html>";
        wb_desc.loadData(text, "text/html", "utf-8");
        wb_desc.setEnabled(false);
        bt_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                termsAndCondition.accept();
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                termsAndCondition.cancel();
                dialog.dismiss();
            }
        });
        dialog.show();


        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.40);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);

    }
}