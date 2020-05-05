insert into artikels(naam,aankoopprijs,verkoopprijs,soort,houdbaarheid,artikelgroepid) values('testFood',10,20,"F",10,(select id from artikelgroepen where naam='testFoodGroep'));
insert into artikels(naam,aankoopprijs,verkoopprijs,soort,garantie,artikelgroepid) values('testNonFood',100,120,"NF",10,(select id from artikelgroepen where naam='testNonGroep'));
