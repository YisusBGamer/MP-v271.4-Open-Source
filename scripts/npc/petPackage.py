# @pet - pet package: a pet of your choice with every pet skill unlocked, or unlock every skill on a
# pet you already have. See PetPackageModule (prices by tier: plain, set, Wonder, Luna).
from net.swordie.ms.client.character import PetPackageModule

fmt = PetPackageModule.format
unlockPrice = fmt(PetPackageModule.getUnlockPrice())
TIER_NOTES = {
    "PLAIN": "Pets with no set skill.",
    "SET": "Pets of a 3-pet set: each one you equip from the set raises its skill (#b+3 to +7 ATT and M.ATT#k).",
    "WONDER": "Wonder pets: a stronger set skill (#b+5 to +9 ATT and M.ATT#k).",
    "LUNA": "Luna pets, blessed by moonlight: the strongest set skill (#b+8 to +18 ATT and M.ATT#k) and a wider "
            "item pickup range with the whole set.",
}


def price_of(petId):
    return fmt(PetPackageModule.getPrice(petId))


def pick_pet(pets, title, note=""):
    text = "#e#b{}#k#n\r\n".format(title)
    if note:
        text += note + "\r\n"
    text += "\r\n"
    for petId in pets:
        text += "#L{}##i{}# #b#t{}##k  {} mesos#l\r\n".format(petId, petId, petId, price_of(petId))
    text += "\r\n#L1##rBack#k#l"
    sel = sm.sendNext(text)
    return sel if pets.contains(sel) else None


def confirm_and_buy(petId):
    if sm.sendAskYesNo("Buy #i{}# #b#t{}##k with #eevery pet skill#n for #b{} mesos#k?".format(petId, petId, price_of(petId))):
        sm.sendSayOkay(PetPackageModule.buy(chr, petId))
        return True
    return False


def tier_page():
    tiers = PetPackageModule.getTiers()
    text = "#e#bPets by kind#k#n\r\nSet pets give a skill that grows as you equip more pets of the same set (up to 3).\r\n\r\n"
    for i in range(len(tiers)):
        t = tiers[i]
        text += "#L{}##b{}#k  {} mesos each#l\r\n".format(i, t.getTitle(), fmt(t.getPrice()))
    text += "\r\n#L99##rBack#k#l"
    i = sm.sendNext(text)
    if 0 <= i < len(tiers):
        t = tiers[i]
        return pick_pet(PetPackageModule.getByTier(t.name()), t.getTitle() + " (newest first)", TIER_NOTES.get(t.name(), ""))
    return None


while True:
    sel = sm.sendNext("#e#bPet Package#k#n\r\n"
                      "A pet of your choice with #eevery pet skill#n unlocked at once: item pickup, expanded range, "
                      "auto move, auto HP/MP, auto buff, auto feed, ignore items and more.\r\n"
                      "Price: #b{}#k to #b{} mesos#k, by kind of pet\r\n\r\n"
                      "#L0##bChoose from popular pets#l\r\n"
                      "#L5#Browse by kind (plain, set, Wonder and Luna pets)#l\r\n"
                      "#L1#Search for a pet by name#l\r\n"
                      "#L2#Unlock every skill on a pet I already have ({} mesos)#l\r\n"
                      "#L3#Buy pet food, snacks and #t5380000##l\r\n"
                      "#L4#Evolve a pet#l".format(fmt(PetPackageModule.Tier.PLAIN.getPrice()),
                                                  fmt(PetPackageModule.Tier.LUNA.getPrice()), unlockPrice))
    if sel == 0:
        petId = pick_pet(PetPackageModule.getFeatured(), "Popular Pets")
        if petId is not None and confirm_and_buy(petId):
            break
    elif sel == 5:
        petId = tier_page()
        if petId is not None and confirm_and_buy(petId):
            break
    elif sel == 1:
        query = sm.sendAskText("Type part of the pet's name (for example: #bkitty#k, #bdragon#k, #bpenguin#k).", "", 2, 20)
        results = PetPackageModule.search(query)
        if results.isEmpty():
            sm.sendNext("I couldn't find a pet called \"{}\".".format(query))
            continue
        petId = pick_pet(results, "Pets matching \"{}\"".format(query))
        if petId is not None and confirm_and_buy(petId):
            break
    elif sel == 2:
        pets = PetPackageModule.getUpgradablePets(chr)
        if pets.isEmpty():
            sm.sendNext("All your pets already have every skill, or you don't have a pet yet.")
            continue
        text = "#e#bYour Pets#k#n\r\nWhich pet should get every skill (#b{} mesos#k)?\r\n\r\n".format(unlockPrice)
        for i in range(pets.size()):
            pet = pets.get(i)
            text += "#L{}##i{}# #b{}#k#l\r\n".format(i, pet.getItemId(), pet.getName())
        text += "\r\n#L99##rBack#k#l"
        i = sm.sendNext(text)
        if 0 <= i < pets.size():
            sm.sendSayOkay(PetPackageModule.upgrade(chr, pets.get(i)))
            break
    elif sel == 3:
        text = "#e#bPet Shop#k#n\r\nYou have #b{}#k mesos.\r\n\r\n".format(fmt(chr.getMoney()))
        for i in range(PetPackageModule.getShopSize()):
            itemId = PetPackageModule.getShopItem(i)
            text += "#L{}##i{}# #b#t{}##k  {} mesos each#l\r\n".format(
                i, itemId, itemId, fmt(PetPackageModule.getShopPrice(i)))
        text += "\r\n#L99##rBack#k#l"
        i = sm.sendNext(text)
        if 0 <= i < PetPackageModule.getShopSize():
            most = PetPackageModule.getMaxBuyable(chr, i)
            if most < 1:
                sm.sendNext("You don't have enough mesos for that.")
                continue
            itemId = PetPackageModule.getShopItem(i)
            n = sm.sendAskNumber("How many #b#t{}##k would you like? ({} mesos each)".format(
                itemId, fmt(PetPackageModule.getShopPrice(i))), 1, 1, most)
            sm.sendNext(PetPackageModule.buyShopItem(chr, i, n))
    elif sel == 4:
        pets = PetPackageModule.getEvolvablePets(chr)
        if pets.isEmpty():
            sm.sendNext("None of your pets can evolve. Evolving pets, like the #bBaby Dragon#k, evolve with "
                        "#b#t5380000##k once they reach the right pet level.")
            continue
        text = "#e#bEvolve a Pet#k#n\r\nWhich pet would you like to evolve?\r\n\r\n"
        for i in range(pets.size()):
            pet = pets.get(i)
            text += "#L{}##i{}# #b{}#k (Lv. {} / needs Lv. {})#l\r\n".format(
                i, pet.getItemId(), pet.getName(), pet.getLevel(), pet.getEvolutionLevel())
        text += "\r\n#L99##rBack#k#l"
        i = sm.sendNext(text)
        if 0 <= i < pets.size():
            sm.sendSayOkay(PetPackageModule.evolve(chr, pets.get(i)))
            break
    else:
        break
