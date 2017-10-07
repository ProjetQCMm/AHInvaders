

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.Scanner;

/*
  source :
   https://github.com/DV8FromTheWorld/JDA/wiki
   https://www.mcdev.fr/threads/créer-votre-premier-bot-discord-en-java.1315/
   https://github.com/DV8FromTheWorld/JDA/blob/master/src/examples/java/MessageListenerExample.java (best)
 */

public class MainBot extends ListenerAdapter {

    // private JDA jda;
    private boolean stop = false;

    public static void main(String[] args) throws LoginException, InterruptedException, RateLimitedException {
        if (args.length < 1) {
            System.out.println("abscence du token");
            return;
        }
        JDA jda = new JDABuilder(AccountType.BOT).setToken(args[0]).setBulkDeleteSplittingEnabled(false).buildBlocking();
        jda.addEventListener(new MainBot());

       /* while (!stop) {
            Scanner scanner = new Scanner(System.in);
            String cmd = scanner.next();
            if (cmd.equalsIgnoreCase("stop")) {
                jda.shutdown();
                stop = true;
            }
        }*/
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        /*if (event.isFromType(ChannelType.TEXT)) {
            System.out.printf("[%s][%s] %#s: %s%n", event.getGuild().getName(),
                    event.getChannel().getName(), event.getAuthor(), event.getMessage().getContent());
        }else{
            System.out.printf("[PM] %#s: %s%n", event.getAuthor(), event.getMessage().getContent());
        }*/

        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
        //  This could be a TextChannel, PrivateChannel, or Group!

        String msg = message.getContent();

        boolean bot = author.isBot();                    //This boolean is useful to determine if the User that
        // sent the Message is a BOT or not!


        if (event.isFromType(ChannelType.TEXT) && !bot) {         //If this message was sent to a Guild TextChannel


            Guild guild = event.getGuild();             //The Guild that this message was sent in. (note, in the API, Guilds are Servers)
            TextChannel textChannel = event.getTextChannel(); //The TextChannel that this message was sent to.
            Member member = event.getMember();          //This Member that sent the message. Contains Guild specific information about the User!

            String name;
            if (message.isWebhookMessage()) {
                name = author.getName();                //If this is a Webhook message, then there is no Member associated
            }                                           // with the User, thus we default to the author for name.
            else {
                name = member.getEffectiveName();       //This will either use the Member's nickname if they have one,
            }                                           // otherwise it will default to their username. (User#getName())

            System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, msg);

           /*if (name.equals("Khawkey"))
                channel.sendMessage("Le maitre a parlé").queue();
            else
                channel.sendMessage(name.toString() + " devrait moins parler").queue();*/
           //channel.deleteMessageById(message.getId());

            if(msg.toUpperCase().contains("PHP"))
                channel.sendMessage("PHP, la blague du siecle").queue();


            message.editMessage("fff").queue();

        }

        /*if (msg.equals("$ping"))
        {
            //This will send a message, "pong!", by constructing a RestAction and "queueing" the action with the Requester.
            // By calling queue(), we send the Request to the Requester which will send it to discord. Using queue() or any
            // of its different forms will handle ratelimiting for you automatically!

            channel.sendMessage("pong!").queue();
        }*/


    }

}