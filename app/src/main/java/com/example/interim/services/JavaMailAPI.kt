package com.teamcreative.javamailapidemo

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class JavaMailAPI    //Constructor
    (//Variables
    private val mContext: Context,
    private val mEmail: String,
    private val mSubject: String,
    private val mMessage: String
) : AsyncTask<Void?, Void?, Void?>() {
    private var mSession: Session? = null
    private var mProgressDialog: ProgressDialog? = null

    override fun doInBackground(vararg params: Void?): Void? {
        //Creating properties
        val props = Properties()

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props["mail.smtp.host"] = "smtp.gmail.com"
        props["mail.smtp.socketFactory.port"] = "465"
        props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.port"] = "465"

        //Creating a new session
        mSession = Session.getDefaultInstance(props,
            object : Authenticator() {
                //Authenticating the password
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(
                        "hai811i.progmobile@gmail.com",
                        "maaxlphkqrybaxcs"
                    )
                }
            })
        try {
            //Creating MimeMessage object
            val mm = MimeMessage(mSession)

            //Setting sender address
            mm.setFrom(InternetAddress("hai811i.progmobile@gmail.com"))
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, InternetAddress(mEmail))
            //Adding subject
            mm.subject = mSubject
            //Adding message
            mm.setText(mMessage)
            //Sending email
            Transport.send(mm)
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
        return null
    }

    override fun onPreExecute() {
        super.onPreExecute()
        //Show progress dialog while sending email
        mProgressDialog =
            ProgressDialog.show(mContext, "Sending message", "Please wait...", false, false)
    }

    override fun onPostExecute(aVoid: Void?) {
        super.onPostExecute(aVoid)
        //Dismiss progress dialog when message successfully send
        mProgressDialog!!.dismiss()

        //Show success toast
        Toast.makeText(mContext, "Message Sent", Toast.LENGTH_SHORT).show()
    }

}