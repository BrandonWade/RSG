package rsg.objects;

import org.eclipse.swt.graphics.Image;

public class GameCharacter extends GameObject
{	
	private int health;
	private int maxHealth;
	private int value; // perhaps a certain amount of points associated to a character for winning a battle
	
	public GameCharacter(String code, String name, Image image, int health, int maxHealth, int value, String interactText)
	{
		super(code, name, image, false, interactText);
		this.health = health;
		this.maxHealth = maxHealth;
		this.value = value;
	}

	public int getHealth()
	{
		return health;
	}
	
	public int getMaxHealth()
	{
		return maxHealth;
	}

	public int getValue()
	{
		return value;
	}
	
	public int getAttack()
	{
		return (int) (Math.random() * value);
	}
	
	public int setMaxHealth()
	{
		setHealth(maxHealth);
		
		return health;
	}
	
	public int setHealth(int amt)
	{
		if (amt > maxHealth)
		{
			health = maxHealth;
		}
		else if (amt < 0)
		{
			health = 0;
		}
		else
		{
			health = amt;
		}
		
		return health;
	}

	public int decreaseHealth(int amt)
	{
		int newHealth = health;

		if (amt > 0)
		{
			newHealth = health - amt;

			if (newHealth <= 0)
			{
				newHealth = 0;
			}
		}
		
		health = newHealth;
		
		return newHealth;
	}

	public int increaseHealth(int amt)
	{
		if (amt > 0)
		{
			health = health + amt;
			
			if (health > maxHealth)
			{
				health = maxHealth;
			}
		}
		return health;
	}
}