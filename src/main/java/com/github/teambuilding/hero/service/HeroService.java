package com.github.teambuilding.hero.service;

import javax.inject.Singleton;

import com.github.teambuilding.bomb.BombService;
import com.github.teambuilding.hero.model.Hero;
import com.github.teambuilding.hero.model.SaboteurHero;
import com.github.teambuilding.hero.model.SniperHero;
import com.github.teambuilding.hero.model.SpyHero;
import com.github.teambuilding.hero.model.TankHero;
import com.github.teambuilding.utility.Position;

@Singleton
public class HeroService {

	private TankHero tank;
	private SniperHero sniper;
	private SpyHero spy;
	private SaboteurHero saboteur;
	
	private Hero[] order;
	
	private BaseHeroService baseHeroService;
	private MoveHeroService moveHeroService;
	private KillHeroService killHeroService;
	private SwapHeroOrderService swapHeroOrderService;
	
	public HeroService() {
		generateHeroes();
		generateServices();
	}
	
	public String getSign(Position position) {
		return baseHeroService.getSign(position);
	}
	
	public void makeAction(char action, BombService bombService) {
		
		if (action == 'a' || action == 'w' || action == 'd' || action == 's') {
			moveHeroService.move(action);
		}
		
		if (action == 'f') {
			baseHeroService.setExplode(bombService);
		}
		
		if (action == 'c') {
			swapHeroOrderService.switchOrder('4');
		}
	}
	
	public boolean isHeroAroundPosition(Position position) {
		return baseHeroService.isHeroAroundPosition(position);
	}
	
	public boolean isPositionHero(Position position) {
		return baseHeroService.isPositionHero(position);
	}
	
	public boolean kill() {
		return killHeroService.kill();
	}
	
	public void killAtPosition(Position position) {
		killHeroService.killAtPosition(position);
	}
	
	private void generateHeroes() {
		
		this.tank = new TankHero();
		this.sniper = new SniperHero();
		this.spy = new SpyHero();
		this.saboteur = new SaboteurHero();
		
		this.order = new Hero[4];
		
		this.order[0] = tank;
		this.order[1] = sniper;
		this.order[2] = spy;
		this.order[3] = saboteur;
	}
	
	private void generateServices() {
		
		this.baseHeroService = new BaseHeroService(saboteur, order);
		this.moveHeroService = new MoveHeroService(spy, order);
		this.killHeroService = new KillHeroService(tank, sniper, order);
		this.swapHeroOrderService = new SwapHeroOrderService(order);
	}
}
