package Main.Main;

import java.awt.Color;
import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
	private String prefix = "m";
	
	//5 max users
	int users = 5;
	int itemsANDvariables = 37;
	public long[][] data = new long[users][itemsANDvariables];
	
	//coordinate grid 100x100
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		long id = event.getMember().getIdLong();
		Random r = new Random();
		int percent;
		
		//for testing purposes cooldown is low
		//cooldown = 30
		int toolCooldown = 0;
		//cooldown = 20
		int raidFindCooldown = 0;
		//cooldown = 60
		int raidCooldown = 0;
				
		if (args[0].equalsIgnoreCase(prefix + "ping")) {
		    event.getChannel().sendMessage("pong!").queue();
		}
		
		//mdaily
		else if (args[0].equalsIgnoreCase(prefix + "pong!")) {
		    event.getChannel().sendMessage("ping").queue();
		}
		
		else if (args[0].equalsIgnoreCase(prefix + "startup")) {
			int register = 0;
			
			for (int i=0; i<users; i++) {
				if (data[i][0] == id) {
					
					register = 1;
				    event.getChannel().sendMessage("You already have an account.").queue();
				}
			}
			
			if (register == 0) {
				for (int i=0; i<users; i++) {
					if (data[i][0] == 0) {
						data[i][0] = Long.valueOf(id);
						data[i][1] = r.nextInt(200)-100;
						data[i][2] = r.nextInt(200)-100;
						if (data[i][1] > 0 && data[i][2] > 0) data[i][13] = 1;
						else if (data[i][1] < 0 && data[i][2] > 0) data[i][13] = 2;
						else if (data[i][1] < 0 && data[i][2] < 0) data[i][13] = 3;
						else if (data[i][1] > 0 && data[i][2] < 0) data[i][13] = 4;
						else data[i][13] = 5;
						data[i][5] = 0;
						data[i][8] = 0;
						data[i][11] = 0;
						data[i][17] = 0;
						data[i][32] = 0;
						data[i][33] = 0;
						data[i][35] = 1002;
						data[i][36] = 1002;
						i = users;
					}
				}
			}
			//test
			for (int i=0; i<data.length; i++) {
				for (int j=0; j<data[i].length; j++) {
					System.out.print(data[i][j] + " ");
				}
				System.out.println("");
			}
			//test
		}
		
		else if (args[0].equalsIgnoreCase(prefix + "profile") || (args[0].equalsIgnoreCase(prefix + "p"))) {
			
			try {
				id = event.getMessage().getMentionedMembers().get(0).getIdLong();
			} catch (IndexOutOfBoundsException E){
				System.out.println("No profiles were mentioned");
				id = event.getMember().getIdLong();
			}
			
			for (int i=0; i<users; i++) {
				if (data[i][0] == id) {
					
					String quadrantName = "";
					if (data[i][13] == 1) quadrantName = "The Overworld"; 
					else if (data[i][13] == 2) quadrantName = "The Ocean"; 
					else if (data[i][13] == 3) quadrantName = "The Nether"; 
					else if (data[i][13] == 4) quadrantName = "The End"; 
					else quadrantName = "The Void"; 
					
					EmbedBuilder embed = new EmbedBuilder();
		            embed.setTitle("ID:", "");
		            embed.setDescription(String.valueOf(data[i][0]));
		            embed.addField("Coordinates:", String.valueOf(data[i][1]) + " " + String.valueOf(data[i][2] + 
		            		"\n You are in " + quadrantName), false);
		            embed.addField("Tools:", "Pickaxe Tier: " + String.valueOf(data[i][3]) +
		            		"\n Axe Tier: " + String.valueOf(data[i][6]) + "\n Sword Tier: " + 
		            		String.valueOf(data[i][9]), false);
		            
		            embed.addField("Materials:", 
		            		"\n Wood: " + String.valueOf(data[i][7]) + 
		            		"\n Stone: " + String.valueOf(data[i][4]) + 
		            		"\n Iron: " + String.valueOf(data[i][12]) +
		            		"\n Redstone: " + String.valueOf(data[i][14]) +
		            		"\n String: " + String.valueOf(data[i][10]) +
		            		"\n Gunpowder: " + String.valueOf(data[i][20]) +
		            		"\n Rotten flesh: " + String.valueOf(data[i][21]) +
		            		"\n Slime ball: " + String.valueOf(data[i][22]) +
		            		"\n Pufferfish: " + String.valueOf(data[i][23]) +
		            		"\n Blaze rod: "  + String.valueOf(data[i][24]) +
		            		"\n Ender pearl: " + String.valueOf(data[i][25]) + 
		            		"\n Phantom membrane: " + String.valueOf(data[i][26]) +
		            		"\n Magic Dust: " + String.valueOf(data[i][16]) +
		            		"\n Magma Cream: " + String.valueOf(data[i][27]), false);
		            embed.addField("Items:", 
		            		"\n Basic compass: " + String.valueOf(data[i][15]) +
		            		"\n Compass: " + String.valueOf(data[i][18]) + 
		            		"\n Enchanted Compass: " + String.valueOf(data[i][19]) +
		            		"\n Underwater potion: " + String.valueOf(data[i][28]) +
		            		"\n Nether potion: " + String.valueOf(data[i][29]) +
		            		"\n End potion: " + String.valueOf(data[i][30]) +
		            		"\n Void potion: " + String.valueOf(data[i][31]), false);
		            
		            if (data[i][33] == 1) {
		            	embed.addField("", "You have recently been raided", false); 
		            	data[i][33] = 0;
		            }
		            //add custom color and skin in msettings
		            embed.setColor(Color.CYAN);
		            event.getChannel().sendMessage(embed.build()).queue();
		            embed.clear();
		            
		            data[i][34] = (long) Math.floor((data[i][3] + data[i][6] + data[i][9])/3);
		            
					i = users;
				}
			}
		}
		
		else if (args[0].equalsIgnoreCase(prefix + "mine")|| (args[0].equalsIgnoreCase(prefix + "m"))) {
			for (int i=0; i<users; i++) {
				if (data[i][0] == id) {
					
					int day = event.getMessage().getTimeCreated().getDayOfYear();
					int hour = event.getMessage().getTimeCreated().getHour();
					int minute = event.getMessage().getTimeCreated().getMinute();
					int mineTemp = day*24*60 + hour*60 + minute; 
							
					if (Math.abs(mineTemp - data[i][5]) >= toolCooldown) {
						int ironDrops = 0;
						int redstoneDrops = 0;
						
						int stone = (int) recurDrops(data[i][3],8,16);
						int stoneDrops = r.nextInt(stone)+stone;
						data[i][4] += stoneDrops;
						int magicDustDrops = 0;
						percent = r.nextInt(10);
						if (percent == 8) {
			            	magicDustDrops = 1;
			            	data[i][16] += 1;
						}
						
						if (data[i][3] > 1) {
							int iron = (int) recurDrops(data[i][3],4,4);
							ironDrops = r.nextInt(iron)+iron;
							data[i][12] += ironDrops;
						}
						
						if (data[i][3] > 3) {
							int redstone = (int) recurDrops(data[i][3],2,2);
							redstoneDrops = r.nextInt(redstone)+redstone;
							data[i][14] += redstoneDrops;
						}
						
						EmbedBuilder embed = new EmbedBuilder();
			            embed.setTitle("You used your tier " + String.valueOf(data[i][3]) + " pickaxe and found:", "");
			            
			            embed.setDescription(stoneDrops + " stone \n" + 
			            		ironDrops + " iron \n" + 
			            		redstoneDrops + " redstone \n" +
			            		magicDustDrops + " magic dust");
			            
			            embed.setThumbnail(pickaxeURL(data[i][3]));
			            embed.setColor(Color.GRAY);
			            event.getChannel().sendMessage(embed.build()).queue();
			            embed.clear();
			            
						data[i][5] = mineTemp;
						i = users;
					}
					else {
					    event.getChannel().sendMessage("mmine, cooldown for: " + (toolCooldown-(Math.abs(mineTemp - data[i][5])))+  " minutes.").queue();
					}
				}
			}
		}
		
		else if (args[0].equalsIgnoreCase(prefix + "axe") || (args[0].equalsIgnoreCase(prefix + "a"))) {
			for (int i=0; i<users; i++) {
				if (data[i][0] == id) {
					
					int day = event.getMessage().getTimeCreated().getDayOfYear();
					int hour = event.getMessage().getTimeCreated().getHour();
					int minute = event.getMessage().getTimeCreated().getMinute();
					int axeTemp = day*24*60 + hour*60 + minute; 
							
					if (Math.abs(axeTemp - data[i][8]) >= toolCooldown) {
						
						int wood = (int) recurDrops(data[i][6],8,32);
						int woodDrops = r.nextInt(wood)+wood;
						data[i][7] += woodDrops;
						int magicDustDrops = 0;
						percent = r.nextInt(10);
						if (percent == 8) {
			            	magicDustDrops = 1;
			            	data[i][16] += 1;
						}
						
						EmbedBuilder embed = new EmbedBuilder();
			            embed.setTitle("You used your tier " + String.valueOf(data[i][6]) + " axe and found:", "");
			            
			            embed.setDescription(woodDrops + " wood \n" + 
			            		magicDustDrops + " magic dust");
			            
			            embed.setThumbnail(axeURL(data[i][6]));
			            embed.setColor(Color.GREEN);
			            event.getChannel().sendMessage(embed.build()).queue();
			            embed.clear();
						
						data[i][8] = axeTemp;
						i = users;
					}
					else {
					    event.getChannel().sendMessage("maxe, cooldown for: " + (toolCooldown-(Math.abs(axeTemp - data[i][8]))) + " minutes.").queue();
					}
				}
			}
		}
		
		else if (args[0].equalsIgnoreCase(prefix + "combat") || (args[0].equalsIgnoreCase(prefix + "s"))) {
			for (int i=0; i<users; i++) {
				if (data[i][0] == id) {
					
					int day = event.getMessage().getTimeCreated().getDayOfYear();
					int hour = event.getMessage().getTimeCreated().getHour();
					int minute = event.getMessage().getTimeCreated().getMinute();
					int swordTemp = day*24*60 + hour*60 + minute; 
							
					if (Math.abs(swordTemp - data[i][11]) >= toolCooldown) {
						
						int string = (int) recurDrops(data[i][9],4,8);
						int stringDrops = r.nextInt(string)+string;
						data[i][10] += stringDrops;
						int gunpowder = (int) recurDrops(data[i][9],2,4);
						int gunpowderDrops = r.nextInt(gunpowder)+gunpowder;
						data[i][20] += gunpowderDrops;
						int rottenflesh = (int) recurDrops(data[i][9],1,1);
						int rottenfleshDrops = r.nextInt(rottenflesh)+rottenflesh;
						data[i][21] += rottenfleshDrops;
						int slimeBall = (int) recurDrops(data[i][9],4,6);
						int slimeBallDrops = r.nextInt(slimeBall)+slimeBall;
						data[i][22] += slimeBallDrops;
						int pufferfishDrops = 0;
			            percent = r.nextInt(3);
						if (percent == 2 || percent ==3) {
			            	int pufferfish = (int) recurDrops(data[i][9],0.25,1);
							pufferfishDrops = r.nextInt(pufferfish)+pufferfish;
			            	data[i][23] += pufferfishDrops;
						}
						int blazeRodDrops = 0;
						percent = r.nextInt(3);
						if (percent == 2) {
			            	int blazeRod = (int) recurDrops(data[i][9],0.5,1);
							blazeRodDrops = r.nextInt(blazeRod)+blazeRod;
			            	data[i][24] += blazeRodDrops;
						}
						int enderpearlDrops = 0;
						percent = r.nextInt(2);
						if (percent == 1) {
			            	int enderpearl = (int) recurDrops(data[i][9],0.5,1);
							enderpearlDrops = r.nextInt(enderpearl)+enderpearl;
			            	data[i][25] += enderpearlDrops;
						}
						int phantomMembraneDrops = 0;
						percent = r.nextInt(4);
						if (percent == 3) {
			            	int phantomMembrane = (int) recurDrops(data[i][9],0.25,1);
							phantomMembraneDrops = r.nextInt(phantomMembrane)+phantomMembrane;
			            	data[i][26] += phantomMembraneDrops;
						}
						int magicDustDrops = 0;
						percent = r.nextInt(10);
						if (percent == 8) {
			            	magicDustDrops = 1;
			            	data[i][16] += 1;
						}
						
						
						EmbedBuilder embed = new EmbedBuilder();
			            embed.setTitle("You used your tier " + String.valueOf(data[i][9]) + " sword and found:", "");  
		            	embed.setDescription(stringDrops + " string \n" + 
					            gunpowderDrops + " gunpowder \n" +
					            rottenfleshDrops + " rotten flesh \n" +
					            slimeBallDrops + " slime ball \n" +
					            pufferfishDrops + " pufferfish (Common Drop) \n" +
					            blazeRodDrops + " blaze rod (Rare Drop) \n" +
					            enderpearlDrops + " ender pearl (Uncommon Drop) \n" +
					            phantomMembraneDrops + " phantom membrane (Rare Drop) \n" +
					            magicDustDrops + " magic dust (Epic Drop)");
			            
			            embed.setThumbnail(swordURL(data[i][9]));
			            embed.setColor(Color.RED);
			            event.getChannel().sendMessage(embed.build()).queue();
			            embed.clear();
						
						data[i][11] = swordTemp;
						i = users;
					}
					else {
					    event.getChannel().sendMessage("mcombat, cooldown for: " + (toolCooldown-(Math.abs(swordTemp - data[i][11]))) + " minutes.").queue();
					}
				}
			}
		}
		
		else if (args[0].equalsIgnoreCase(prefix + "raid") || (args[0].equalsIgnoreCase(prefix + "r"))) {
			try {
				for (int i=0; i<users; i++) {
					if (data[i][0] == id) {
						
						int day = event.getMessage().getTimeCreated().getDayOfYear();
						int hour = event.getMessage().getTimeCreated().getHour();
						int minute = event.getMessage().getTimeCreated().getMinute();
						int raidTemp = day*24*60 + hour*60 + minute; 
								
						if (Math.abs(raidTemp - data[i][32]) >= raidCooldown) {
							long raiderID = event.getMessage().getMentionedMembers().get(0).getIdLong();
							
							if (raiderID != id) {
								if (data[i][35] < 1001 && data[i][36] < 1001) {
									for (int j=0; j<users; j++) {
										if (data[j][0] == raiderID) {
											
											int maxDistance;
											if (data[i][19] >= 1) maxDistance = 100;
											else if (data[i][18] >= 1) maxDistance = 50;
											else if (data[i][15] >= 1) maxDistance = 25;
											else maxDistance = 10;
																				
											int x1 = (int) data[i][35];
											int y1 = (int) data[i][36];
											int x2 = (int) data[j][1];
											int y2 = (int) data[j][2];
											
											double distance = Math.sqrt(Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2));
											
											if (distance <= maxDistance) {
												if (data[j][13] == 1) {
													event.getChannel().sendMessage(steal(data[j], data[i], data[i][34])).queue();
												}
												else if (data[j][13] == 2) {
													if (data[i][28] >= 1) {
														event.getChannel().sendMessage(steal(data[j], data[i], data[i][34])).queue();
													}
													else event.getChannel().sendMessage("Missing 1 underwater potion").queue();
												}
												else if (data[j][13] == 3) {
													if (data[i][29] >= 1) {
														event.getChannel().sendMessage(steal(data[j], data[i], data[i][34])).queue();
													}
													else event.getChannel().sendMessage("Missing 1 nether potion").queue();
												}
												else if (data[j][13] == 4) {
													if (data[i][30] >= 1) {
														event.getChannel().sendMessage(steal(data[j], data[i], data[i][34])).queue();
													}
													else event.getChannel().sendMessage("Missing 1 end potion").queue();
												}
												else if (data[j][13] == 5) {
													if (data[i][31] >= 1) {
														event.getChannel().sendMessage(steal(data[j], data[i], data[i][34])).queue();
													}
													else event.getChannel().sendMessage("Missing 1 void potion").queue();
												}
												j = users;
											}
											else event.getChannel().sendMessage("There are no users in your radius").queue(); j = users;
										}
										else event.getChannel().sendMessage("This user does not have an account yet").queue(); j = users;
									}
								}
								else event.getChannel().sendMessage("Use mraid to scan for users first").queue();
							}
							else event.getChannel().sendMessage("You cannot raid yourself").queue();
							
							data[i][32] = raidTemp;
							i = users;
						}
						
						else {
						    event.getChannel().sendMessage("mraid, cooldown for: " + (raidCooldown-(Math.abs(raidTemp - data[i][32]))) + " minutes.").queue();
						}
					}
				}
				
			} catch (IndexOutOfBoundsException E){
				System.out.println("No users were mentioned");
				
				for (int i=0; i<users; i++) {
					if (data[i][0] == id) {
						
						int day = event.getMessage().getTimeCreated().getDayOfYear();
						int hour = event.getMessage().getTimeCreated().getHour();
						int minute = event.getMessage().getTimeCreated().getMinute();
						int raidFindTemp = day*24*60 + hour*60 + minute; 
								
						if (Math.abs(raidFindTemp - data[i][17]) >= raidFindCooldown) {
							int x1 = r.nextInt(200)-100;
							int y1 = r.nextInt(200)-100;
							data[i][35] = x1;
							data[i][36] = y1;
							
							int maxDistance;
							if (data[i][19] >= 1) maxDistance = 100;
							else if (data[i][18] >= 1) maxDistance = 50;
							else if (data[i][15] >= 1) maxDistance = 25;
							else maxDistance = 10;
							
							long raidable[] = new long[users];
							double distance;
							
							for (int j=0; j<users; j++) {
								if (j != i) {
									int x2 = (int) data[j][1];
									int y2 = (int) data[j][2];
									distance = Math.sqrt(Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2));
								}
								else distance = 1000;
								
								if (distance <= maxDistance) {
									raidable[j] = data[j][0];
								}
							}
							int emptyCheck = 0;
							EmbedBuilder embed = new EmbedBuilder();
				            embed.setTitle("Raidable Users at (" + x1 + ", " + y1 + "):" , "");
				            for (int j=0; j<users; j++) {
				            	if (raidable[j] != 0) {
				            		emptyCheck = 1;
				            		embed.addField("","<@" + raidable[j] + ">", false);
				            	}
				            }
				            if (emptyCheck == 0) embed.addField("","No users found in your radius",false);
				            embed.setColor(Color.WHITE);
				            event.getChannel().sendMessage(embed.build()).queue();
				            embed.clear();
				            
				            data[i][17] = raidFindTemp;
							i = users;
						}
						
						else {
							event.getChannel().sendMessage("mraid (scanning), cooldown for: " + (raidFindCooldown-(Math.abs(raidFindTemp - data[i][17]))) + " minutes.").queue();
						}
					}
				}
			}
		}
		
		else if (args[0].equalsIgnoreCase(prefix + "craft") || (args[0].equalsIgnoreCase(prefix + "c"))) {
			for (int i=0; i<users; i++) {
				if (data[i][0] == id) {
					
					//recipies
					i = users;
				}
			}
		}
		
		else if (args[0].equalsIgnoreCase(prefix + "craft.baiscCompass") || (args[0].equalsIgnoreCase(prefix + "c.basicCompass"))) {
			for (int i=0; i<users; i++) {
				if (data[i][0] == id) {
					if (data[i][10] >= 64 && data[i][16] >= 1) {
						data[i][10] -= 64;
						data[i][16] -= 1;
						data[i][15] += 1;
						event.getChannel().sendMessage("Successfully crafted a basicCompass").queue();
						i = users;
					}
					else event.getChannel().sendMessage("You do not have enough materials to craft this item"
							+ "\n Missing: (" + String.valueOf(64-data[i][10]) + " string) (" + 
							String.valueOf(1-data[i][16]) + " magic dust)").queue(); i = users;
				}
			}
		}
		
		else if (args[0].equalsIgnoreCase(prefix + "craft.baiscCompass") || (args[0].equalsIgnoreCase(prefix + "c.basicCompass"))) {
			for (int i=0; i<users; i++) {
				if (data[i][0] == id) {
					if (data[i][10] >= 64 && data[i][16] >= 1) {
						data[i][10] -= 64;
						data[i][16] -= 1;
						data[i][15] += 1;
						event.getChannel().sendMessage("Successfully crafted a basicCompass").queue();
						i = users;
					}
					else event.getChannel().sendMessage("You do not have enough materials to craft this item").queue(); i = users;
				}
			}
		}
		
		else if (args[0].equalsIgnoreCase(prefix + "craft.compass") || (args[0].equalsIgnoreCase(prefix + "c.compass"))) {
			for (int i=0; i<users; i++) {
				if (data[i][0] == id) {
					if (data[i][10] >= 64 && data[i][12] >= 16 && data[i][16] >= 1) {
						data[i][10] -= 64;
						data[i][16] -= 1;
						data[i][12] -= 16;
						data[i][18] += 1;
						event.getChannel().sendMessage("Successfully crafted a compass").queue();
						i = users;
					}
					else event.getChannel().sendMessage("You do not have enough materials to craft this item"
							+ "\n Missing: (" + String.valueOf(64-data[i][10]) + " string) (" + 
							String.valueOf(16-data[i][12]) + " iron) (" + 
							String.valueOf(1-data[i][16]) + " magic dust)").queue(); i = users;
				}
			}
		}
		
		else if (args[0].equalsIgnoreCase(prefix + "craft.enchantedCompass") || (args[0].equalsIgnoreCase(prefix + "c.enchantedCompass"))) {
			for (int i=0; i<users; i++) {
				if (data[i][0] == id) {
					if (data[i][12] >= 32 && data[i][14] >= 32 && data[i][16] >= 1) {
						data[i][12] -= 32;
						data[i][14] -= 32;
						data[i][16] -= 1;
						data[i][19] += 1;
						event.getChannel().sendMessage("Successfully crafted an enchanted compass").queue();
						i = users;
					}
					else event.getChannel().sendMessage("You do not have enough materials to craft this item"
							+ "\n Missing: (" + String.valueOf(32-data[i][12]) + " iron) (" + 
							String.valueOf(32-data[i][14]) + " redstone) (" + 
							String.valueOf(1-data[i][16]) + " magic dust)").queue(); i = users;
				}
			}
		}
		
		else if (args[0].equalsIgnoreCase(prefix + "craft.magmaCream") || (args[0].equalsIgnoreCase(prefix + "c.magmaCream"))) {
            for (int i=0; i<users; i++) {
                if (data[i][0] == id) {
                    if (data[i][22] >= 8 & data[i][24] >= 1) {
                    	data[i][22] -= 8;
                    	data[i][24] -= 1;
                    	data[i][27] += 1;
                        event.getChannel().sendMessage("Successfully crafted 8 magma cream").queue();
                        i = users;
                    }
                    else event.getChannel().sendMessage("You do not have enough materials to craft this item"
                    		+ "\n Missing: (" + String.valueOf(8-data[i][22]) + " slime ball) (" + 
                    		String.valueOf(1-data[i][24]) + " blaze rod)").queue(); i = users;
                }
            }
        }
		
		else if (args[0].equalsIgnoreCase(prefix + "craft.underwaterPotion") || (args[0].equalsIgnoreCase(prefix + "c.underwaterPotion"))) {
            for (int i=0; i<users; i++) {
                if (data[i][0] == id) {
                    if (data[i][23] >= 2 && data[i][21] >= 8) {
                    	data[i][23] -= 2;
                    	data[i][21] -= 8;
                    	data[i][28] += 1;
                        event.getChannel().sendMessage("Successfully crafted an underwater potion").queue();
                        i = users;
                    }
                    else event.getChannel().sendMessage("You do not have enough materials to craft this item"
                    		+ "\n Missing: (" + String.valueOf(2-data[i][23]) + " pufferfish) (" +
                    		String.valueOf(8-data[i][21]) + " rotten flesh)").queue(); i = users;
                }
            }
        }
		
		else if (args[0].equalsIgnoreCase(prefix + "craft.netherPotion") || (args[0].equalsIgnoreCase(prefix + "c.netherPotion"))) {
            for (int i=0; i<users; i++) {
                if (data[i][0] == id) {
                    if (data[i][27] >= 4 && data[i][24] >= 1 && data[i][21] >= 16) {
                    	data[i][27] -= 4;
                    	data[i][24] -= 1;
                    	data[i][21] -= 16;
                    	data[i][29] += 1;
                        event.getChannel().sendMessage("Successfully crafted a nether potion").queue();
                        i = users;
                    }
                    else event.getChannel().sendMessage("You do not have enough materials to craft this item"
                    		+ "\n Missing: (" + String.valueOf(4-data[i][27]) + " magma cream) (" +
                    		String.valueOf(1-data[i][24]) + " blaze rod) (" + 
                    		String.valueOf(16-data[i][21]) + " rotten flesh)").queue(); i = users;
                }
            }
        }
		
		else if (args[0].equalsIgnoreCase(prefix + "craft.endPotion") || (args[0].equalsIgnoreCase(prefix + "c.endPotion"))) {
            for (int i=0; i<users; i++) {
                if (data[i][0] == id) {
                    if (data[i][25] >= 4 && data[i][26] >= 1 && data[i][24] >= 1) {
                    	data[i][25] -= 4;
                    	data[i][26] -= 1;
                    	data[i][24] -= 1;
                    	data[i][30] += 1;
                        event.getChannel().sendMessage("Successfully crafted an end potion").queue();
                        i = users;
                    }
                    else event.getChannel().sendMessage("You do not have enough materials to craft this item"
                    		+ "\n Missing: (" + String.valueOf(4-data[i][25]) + " ender pearl) (" +
                    		String.valueOf(1-data[i][24]) + " blaze rod) (" + 
                    		String.valueOf(1-data[i][26]) + " phantom membrane)").queue(); i = users;
                }
            }
        }
		
		else if (args[0].equalsIgnoreCase(prefix + "craft.voidPotion") || (args[0].equalsIgnoreCase(prefix + "c.voidPotion"))) {
            for (int i=0; i<users; i++) {
                if (data[i][0] == id) {
                    if (data[i][25] >= 8 && data[i][26] >= 2 && data[i][24] >= 2) {
                    	data[i][25] -= 8;
                    	data[i][26] -= 2;
                    	data[i][24] -= 2;
                    	data[i][31] += 1;
                        event.getChannel().sendMessage("Successfully crafted a void potion").queue();
                        i = users;
                    }
                    else event.getChannel().sendMessage("You do not have enough materials to craft this item"
                    		+ "\n Missing: (" + String.valueOf(8-data[i][25]) + " ender pearl) (" +
                    		String.valueOf(2-data[i][24]) + " blaze rod) (" + 
                    		String.valueOf(2-data[i][26]) + " phantom membrane)").queue(); i = users;
                }
            }
        }
		
		else if (args[0].equalsIgnoreCase(prefix + "craft.pickaxe1") || (args[0].equalsIgnoreCase(prefix + "c.pickaxe1"))) {
            for (int i=0; i<users; i++) {
                if (data[i][0] == id) {
                    if (data[i][7] >= 640) {
                    	if (data[i][3] < 1) {
                    		data[i][7] -= 640;
                        	data[i][3] = 1;
                            event.getChannel().sendMessage("Successfully crafted a tier 1 pickaxe").queue();
                            i = users;
                    	}
                    	else event.getChannel().sendMessage("Cannot craft a lower tier pickaxe").queue();
                    }
                    else event.getChannel().sendMessage("You do not have enough materials to craft this tool"
                    		+ "\n Missing: (" + String.valueOf(640-data[i][7]) + " wood)").queue(); i = users;
                }
            }
        }
		
		else if (args[0].equalsIgnoreCase(prefix + "craft.pickaxe2") || (args[0].equalsIgnoreCase(prefix + "c.pickaxe2"))) {
            for (int i=0; i<users; i++) {
                if (data[i][0] == id) {
                    if (data[i][4] >= 192 && data[i][7] >= 128) {
                    	if (data[i][3] < 2) {
                    		data[i][4] -= 192;
                    		data[i][7] -= 128;
                        	data[i][3] = 2;
                            event.getChannel().sendMessage("Successfully crafted a tier 2 pickaxe").queue();
                            i = users;
                    	}
                    	else event.getChannel().sendMessage("Cannot craft a lower tier pickaxe").queue();
                    }
                    else event.getChannel().sendMessage("You do not have enough materials to craft this tool"
                    		+ "\n Missing: (" + String.valueOf(192-data[i][4]) + " stone) (" + String.valueOf(128-data[i][7]) + " wood)").queue(); i = users;
                }
            }
        }
		
		else if (args[0].equalsIgnoreCase(prefix + "craft.axe1") || (args[0].equalsIgnoreCase(prefix + "c.axe1"))) {
            for (int i=0; i<users; i++) {
                if (data[i][0] == id) {
                    if (data[i][7] >= 640) {
                    	if (data[i][6] < 1) {
                    		data[i][7] -= 640;
                        	data[i][6] = 1;
                            event.getChannel().sendMessage("Successfully crafted a tier 1 axe").queue();
                            i = users;
                    	}
                    	else event.getChannel().sendMessage("Cannot craft a lower tier axe").queue();
                    }
                    else event.getChannel().sendMessage("You do not have enough materials to craft this tool"
                    		+ "\n Missing: (" + String.valueOf(640-data[i][7]) + " wood)").queue(); i = users;
                }
            }
        }
		
		else if (args[0].equalsIgnoreCase(prefix + "craft.sword1") || (args[0].equalsIgnoreCase(prefix + "c.sword1"))) {
            for (int i=0; i<users; i++) {
                if (data[i][0] == id) {
                    if (data[i][7] >= 640) {
                    	if (data[i][9] < 1) {
                    		data[i][7] -= 640;
                        	data[i][9] = 1;
                            event.getChannel().sendMessage("Successfully crafted a tier 1 sword").queue();
                            i = users;
                    	}
                    	else event.getChannel().sendMessage("Cannot craft a lower tier sword").queue();
                    }
                    else event.getChannel().sendMessage("You do not have enough materials to craft this tool"
                    		+ "\n Missing: (" + String.valueOf(640-data[i][7]) + " wood)").queue(); i = users;
                }
            }
        }
	}
	
	public static double recurDrops (long tier, double add, int base) {
		if (tier == 0) {
			return base;
		}
		else {
			return add+recurDrops(tier-1, add, base);
		}
	}
	
	public static String steal (long[] steal, long[] give, long avgTier) {
		Random r1 = new Random();
		double raidPercent;
		long amount;
		String stole = "You successfully stole: ";
		
		if (avgTier <= 4) raidPercent = 0.95;
		else raidPercent = 0.9;
		
		amount = steal[4] - (long) Math.floor(steal[4] * raidPercent); steal[4] -= amount; give[4] += amount; stole += "(" + amount + " stone) ";
		amount = steal[7] - (long) Math.floor(steal[7] * raidPercent); steal[7] -= amount; give[7] += amount; stole += "(" + amount + " wood) ";
		amount = steal[10] - (long) Math.floor(steal[10] * raidPercent); steal[10] -= amount; give[10] += amount; stole += "(" + amount + " string) ";
		amount = steal[12] - (long) Math.floor(steal[12] * raidPercent); steal[12] -= amount; give[12] += amount; stole += "(" + amount + " iron) ";
		amount = steal[14] - (long) Math.floor(steal[14] * raidPercent); steal[14] -= amount; give[14] += amount; stole += "(" + amount + " redstone) ";
		amount = steal[16] - (long) Math.floor(steal[16] * raidPercent); steal[16] -= amount; give[16] += amount; stole += "(" + amount + " magic dust) ";
		amount = steal[20] - (long) Math.floor(steal[20] * raidPercent); steal[20] -= amount; give[20] += amount; stole += "(" + amount + " gunpowder) ";
		amount = steal[21] - (long) Math.floor(steal[21] * raidPercent); steal[21] -= amount; give[21] += amount; stole += "(" + amount + " rottenflesh) ";
		amount = steal[22] - (long) Math.floor(steal[22] * raidPercent); steal[22] -= amount; give[22] += amount; stole += "(" + amount + " slime ball) ";
		amount = steal[23] - (long) Math.floor(steal[23] * raidPercent); steal[23] -= amount; give[23] += amount; stole += "(" + amount + " pufferfish) "; 
		amount = steal[24] - (long) Math.floor(steal[24] * raidPercent); steal[24] -= amount; give[24] += amount; stole += "(" + amount + " blaze rod) ";
		amount = steal[25] - (long) Math.floor(steal[25] * raidPercent); steal[25] -= amount; give[25] += amount; stole += "(" + amount + " ender pearl) ";
		amount = steal[26] - (long) Math.floor(steal[26] * raidPercent); steal[26] -= amount; give[26] += amount; stole += "(" + amount + " phantom membrane) ";
		amount = steal[27] - (long) Math.floor(steal[27] * raidPercent); steal[27] -= amount; give[27] += amount; stole += "(" + amount + " magma cream)";
				
		steal[33] = 1;
		steal[1] = r1.nextInt(200)-100;
		steal[2] = r1.nextInt(200)-100;
		if (steal[1] > 0 && steal[2] > 0) steal[13] = 1;
		else if (steal[1] < 0 && steal[2] > 0) steal[13] = 2;
		else if (steal[1] < 0 && steal[2] < 0) steal[13] = 3;
		else if (steal[1] > 0 && steal[2] < 0) steal[13] = 4;
		else steal[13] = 5;
		
		return stole;
	}
	
	public static String pickaxeURL (long tier) {
		if (tier == 0) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/0/0b/Wooden_Pickaxe_JE2_BE2.png/revision/latest?cb=20200217231203";
		if (tier == 1) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/6/64/Enchanted_Wooden_Pickaxe.gif/revision/latest/scale-to-width-down/250?cb=20201120003805";
		if (tier == 2) return "https://spng.pngfind.com/pngs/s/40-400490_minecraft-kazma-png-minecraft-stone-pickaxe-transparent-png.png";
		if (tier == 3) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/6/62/Enchanted_Stone_Pickaxe.gif/revision/latest/scale-to-width-down/250?cb=20201120003754";
		if (tier == 4) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/d/d1/Iron_Pickaxe_JE3_BE2.png/revision/latest/scale-to-width-down/160?cb=20200105053011";
		if (tier == 5) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/f/fd/Enchanted_Iron_Pickaxe.gif/revision/latest?cb=20201118221334";
		if (tier == 6) return "https://www.pinpng.com/pngs/m/40-400266_minecraft-gold-pickaxe-hd-png-download.png";
		if (tier == 7) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/d/dd/Enchanted_Golden_Pickaxe.gif/revision/latest/scale-to-width-down/250?cb=20201118111836";
		if (tier == 8) return "https://p1.hiclipart.com/preview/38/630/500/minecraft-diamond-pickaxe-minecraft-diamond-axe-png-clipart.jpg";
		if (tier == 9) return "https://static.wikia.nocookie.net/minecraft/images/3/39/EnchantedDiamondPickaxeNew.gif/revision/latest/scale-to-width-down/250?cb=20200117002613";
		else return " ";
	}
	
	public static String axeURL (long tier) {
		if (tier == 0) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/5/56/Wooden_Axe_JE2_BE2.png/revision/latest/scale-to-width-down/160?cb=20200217234355";
		if (tier == 1) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/e/e0/Enchanted_Wooden_Axe.gif/revision/latest/scale-to-width-down/160?cb=20201120003802";
		if (tier == 2) return "https://toppng.com/uploads/preview/stone-axe-machado-minecraft-11563874958nhfysnfwv3.png";
		if (tier == 3) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/d/d0/Enchanted_Stone_Axe.gif/revision/latest/scale-to-width-down/160?cb=20201120003750";
		if (tier == 4) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/5/5e/Iron_Axe_JE5_BE2.png/revision/latest/scale-to-width-down/160?cb=20200217234438";
		if (tier == 5) return "https://minecraft-max.net/upload/iblock/0a3/0a308dfc0ff4bc8972f55c73360df9dd.png";
		if (tier == 6) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/5/5c/Golden_Axe_JE2_BE1.png/revision/latest?cb=20200128134551";
		if (tier == 7) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/d/d4/Enchanted_Golden_Axe.gif/revision/latest/scale-to-width-down/160?cb=20201118111731";
		if (tier == 8) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/4/40/Diamond_Axe_JE3_BE3.png/revision/latest?cb=20200226193844";
		if (tier == 9) return "https://i.imgur.com/J66rSvS.png";
		else return " ";
	}
	
	public static String swordURL (long tier) {
		if (tier == 0) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/d/d5/Wooden_Sword_JE2_BE2.png/revision/latest/scale-to-width-down/160?cb=20200217235747";
		if (tier == 1) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/c/c1/Enchanted_Wooden_Sword.gif/revision/latest/scale-to-width-down/250?cb=20201120003809";
		if (tier == 2) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/b/b1/Stone_Sword_JE2_BE2.png/revision/latest?cb=20200217235849";
		if (tier == 3) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/e/ed/Enchanted_Stone_Sword.gif/revision/latest/scale-to-width-down/250?cb=20201120003758";
		if (tier == 4) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/8/8e/Iron_Sword_JE2_BE2.png/revision/latest/scale-to-width-down/160?cb=20200217235910";
		if (tier == 5) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/d/de/Enchanted_Iron_Sword.gif/revision/latest/scale-to-width-down/250?cb=20201118221344";
		if (tier == 6) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/d/db/Golden_Sword_JE3_BE2.png/revision/latest/scale-to-width-down/160?cb=20200217235825";
		if (tier == 7) return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/e/ef/Enchanted_Golden_Sword.gif/revision/latest/scale-to-width-down/250?cb=20201118111854";
		if (tier == 8) return "http://assets.stickpng.com/images/580b57fcd9996e24bc43c301.png";
		if (tier == 9) return "https://www.vhv.rs/dpng/d/515-5152214_enchanted-minecraft-diamond-sword-hd-png-download.png";
		else return " ";
	}
}