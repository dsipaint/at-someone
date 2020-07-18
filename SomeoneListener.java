import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SomeoneListener extends ListenerAdapter
{
	private String[] blacklisted_members = {
			"232952565334016000", //flared
			"329979171562586124" //puchimi
	};
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		if(e.getGuild().getId().equals("614260951167795204") && !e.getChannel().getId().equals("648667030509191209"))
			return;
		
		String msg = e.getMessage().getContentRaw();
		String[] args = msg.split(" ");
		Random r = new Random();
		
		if(msg.matches(".*@[Ss]omeone.*"))
		{
			Member m = getBlacklist(e.getGuild()).get(r.nextInt(getBlacklist(e.getGuild()).size()));
			e.getChannel().sendMessage(m.getAsMention()).queue();
			return;
		}
		
		if(args[0].equalsIgnoreCase(Main.PREFIX + "stop") && isStaff(e.getMember()))
		{
			e.getJDA().shutdown();
			System.exit(0);
		}
	}
	
	private boolean isStaff(Member m)
	{
		if(m.getId().equals("475859944101380106"))
			return true;
		
		for(Role r : m.getRoles())
		{
			if(r.getId().equals("614262194112036895") || r.getId().equals("637607873433698314")
					|| r.getId().equals("634442520310448151") || r.getId().equals("627946235478802433")
					|| r.getId().equals("632427837466476554"))
				return true;
		}
		
		return false;
	}
	
	private LinkedList<Member> getBlacklist(Guild g)
	{
		LinkedList<Member> blacklist = new LinkedList<Member>(g.getMembers());
		
		for(String id : blacklisted_members)
		{
			if(g.getMemberById(id) != null)
				blacklist.remove(g.getMemberById(id));
		}
		
		return blacklist;
	}
}
