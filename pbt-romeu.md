
 C'est quoi une propriété

 C'est quoi PBT
 - à quoi ca sert?

 Pourquoi et comment?


 Une définition
 Une propriété c'est
 - c'est une qualité
 - quelque chose qu'on croit vrai vis à vis de code
 - aussi pari de mauvaise foi

 ex.
 Si je fais une somme, je pourrais crois que c'est vrai quand
 on ajoute deux chiffres c'est vrai que c'est plus grand
 - autant dans notre code ce ne serait pas vrai autant dans les maths ca pourrait l'etre

 Séparer d'une tautologie
 - c'est quelque chose qui est vrai comme un pléonasme vcieux
 - "Monter vers le haut"

 Une propriété est une chose que je crois qui est vrais dans mon code
 mais que je ne crois pas qui est une tautologie

 PBT
 - un pari
 - au meme titre que quand on paie une assurance on fait un pari
 - l'assureur parie qu'on n'a pas d'incident
 - de temps en temps ou gagne le pari et l'assureur perd

 Je vais un pari sur une propriété
 le PBT framework essaie de parier que c'est faux

 Différence centrale
 uand on fait des tests sur example
 - vert = passé
 - j'essaie toujours le même truc

 PBT
 - vert = je n'ai pas trouvé de contre exemple cette fois ci
 - rouge = j'ai trouvé un contre exemple
 - j'essaie jamais le même truc

 Générateurs
 - générer des entrée
 assumptions
 - vérifie si valide pour ma propriété
 assertions

 Tension entre créer un générateur
 ou utiliser un générateur existant avec une assumption
 selon les echantillons

 Example base testing vs PBT

 ex kata bowling


"sans spare ni strike" {
    play(1,2,3,4) shouldBe 10
}


 Dans un test sur exemple on
 - compare: input et output

 Test paramétré si j'arrive à mettre un truc en dur
 et faire varier d'autres trucs
 -> de input => output
 -> pour tel input => comportement (une moitié de règle)

 L'archéologie de mon code
 la personne tombe sur mon code et ne connait pas les règles métier
 (TDD au dela de l'intro — Alpescraft)

 Est-ce que si la personne tombe sur mon code
 c'est suffisant pour qu'elle comprenne les règles?

 Ex. créer un pattern visuel pour les gens

 Niveau d'après : le PBT
 - Voir plusieurs propriété qui constituent une règle métier
 - condition de sortie
 PLutot que de fair de la démocratie représentative, on peut faire démocratie réelle

 Ex. bowling on peut faire plein de générateurs différents
 Générateur de value object
 - la plupart des VO, sont avec une contrainte, une propriété
 - dès que je commence à faire beacuoup de primitives en PBT, on peut avoir Value Object envie

 PLutot que 3 exemples qui représentent une population
 je peux avoir un générateur qui permet d'avoir une population

 je suis en train de faire pari
 - je crois que pour toutes les vals du gen, le test passe

 Trouve très rapidement des cas limites, PBT te casse tes illusions
 - des fois => dialogues très marrant avec les PO
   - Souvent, la code base a des règles métier que personne ne connait
   - Des fois des choses codées sont des bugs (ou le métier sont des bigs)
   - Le code dit des choses => on en discute

 En général tu ne connais pas toutes les règles métier de ton code
 Très vite, on est plus dans cette situation là.
 PBT => outil de navigation archéo aussi

 Tu fais des paris
 PBT peut servir à faire des tests de caractérisation
 Créer le minimum de refactoring avant pour pouvoir isoler un bout que je veut tester
 (Splint refactoring)

 1. “Emettre règle métier” — Pour déclarer une règle métier dans mon code (bcp double boucle) — BDD, ATDD, Spec by example
 2. “Complémentaire aux Tests paramétrés” — Tests paramétrés qui convergent vers un générateur et une propriété
 3. “” —
 4. “” —

 On peut très vite commencer avec PBT. On obtient rapidement de la valeur avec
 Ce qu'on voit dans le code de prod est très différent au code de kata

 un patient, un nom, age, ...
 dans le test l'important c'est l'age, tout le reste on le génère avec des valeurs aléatoire
 Beaucoup de truc sont des valeurs en dur. Si le reste je m'en fiche de la valeur, tout le reste peut être aléatoire.

 != Fixtures : rendent le code plus beau et permet de réduire le nombre de fois qu'on crée la donnée
 Remplacer mes fixtures par des générateurs
 -> Sert pour documenter : “donne moi un patient dont l'age est dans cette fenêtre et le reste n'est pas important pour moi”

 💡 mal compris regle métier
 💡 bug dans mon code
 💡 je dois changer mon générateur?

 sert les économies d'attention
 - change ce qui est plus cher ou moins cher avec ton code
   - ex. plus dur de faire un code intestable (TDD)


 Quelques limites
 - Il y a des frameworks de Théorie, qui essayent de générer toutes les valeurs, model checking...
   - Si faire une théorie n'est pas un pb de performance, pas nécessaire de faire un générateur alétoire

 PBT te donne un autre limite plus loin
 Ex. le jeu du quatro
 - très très simple
   - 16 pièces possibles dans un tableau de 4x4
 - la combinatoire de possibilité, avec les valeurs aléatoires, tu es perdu très vite

 Diff avec un mouvement brownien
 - couvrir une surface avec de l'aléatoire
 - en aléatoire pur, il arrive à couvrir un espace en 2D
 - un oiseau brownien, très peu de probabilté qu'il couvre tout l'espace
 Espace de valeur trop grand, tu n'a pas la garantie de tout couvrir

 Est-ce que je ne dépasse pas la limite de l'espace brownien?

 Chaque état compte
 à partir du moment ou tu depasse un certain seuil tu sors du mouvement brownien

 Une technique pour tenter de répondre à ce pb : SAT



 3 niveaux d'artillerie
 - Example
 - PBT
 - SAT

 💡 Romeu
 1. et si mes tests sont trop lents?
   - ❌ fais des tests plus rapide
   - au pire du pire, générer un cas aléatoire aura la meme perf qu'une execution de test
   - réduis le nombre de trucs que tu génère

 2. Comment tu sais là ou je ne devrais pas l'utilser?
   - Là ou on peut faire la différence entre propriété et tautologie
   - Lorsque ton code de test a le même comportement que ton test (test une tautologie et pas une propriété). Pour ca que je ne fais pas de PBT avec FizzBuzz

 3. Quand j'exécute ma CI et 2 ans après le truc me trouve un bug au moment ou j'allais faire ma release
   - La même chose que tu ferais que si un testeur était arrivé en te disant qu'il y avait un bug


 4. différence entre PBT et un fuzzer?
   - Fuzzer ne connait rien de ton code (souvent utilisé pour tester de la sécurité)
     - Gigantesque brute force avec des données aléatoire. Plus fort que l'humain car il est très con et a une patience infinie
   - PBT connait tes règles métier (tu décris des choses de ton métier)

 Ex.
 Application 3 tiers
 Requete -> controller -> adaptateur persistance
 Adapter
 - être capable de persister une donnée
   - si j'écris une donnée je peux la lire
   - intégrité : ce que je récupère est ce que j'ai mis
   - idempotence
     - si j'envoie la même commande 10 fais est ce que l'effet de bord n'a eu lieu qu'une fois


 Dans du code très bouchonné
 - les propriétés pourraient être = “est-ce que tel message a été envoyé?”


 Assumption: utiliser avec parcimonie
 - quand tu as très peu de choses à filtrer



 Ex. un meme créateur ne peut pas créer 2 dossiers avec le même nom

 💡 Quelle est la propriété qu'on veut tester
 - ex en math: somme => tester l'associativité
 a + b + c = a + c +b



