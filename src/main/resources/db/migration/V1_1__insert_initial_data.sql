INSERT INTO glass (name, description)
VALUES
    ('Default Glass', 'Generic glass, unspecified use'),
    ('Highball', 'A tall glass used for mixed drinks, typically filled with ice and soda or tonic water'),
    ('Martini', 'A stemmed glass used for serving martinis, typically without ice and with a broad rim'),
    ('Shot', 'A small glass used for serving spirits or liqueurs in small quantities, often consumed in one go'),
    ('Hurricane', 'A large, curved glass designed for tropical cocktails, typically with a wide bowl to hold fruity, mixed drinks')
ON CONFLICT DO NOTHING;


INSERT INTO ingredient (name, abv, category, description)
VALUES
    ('Rum', 40.0, 'LIQUID', 'Rum is a liquor made by fermenting and then distilling sugarcane molasses or sugarcane juice. The distillate, a clear liquid, is often aged in barrels of oak. Rum originated in the Caribbean in the 17th century, but today it is produced in nearly every major sugar-producing region of the world.'),
    ('Lime Juice', 0.0, 'LIQUID', 'Juice extracted from fresh lime'),
    ('Mint Leaves', 0.0, 'SOLID', 'Fresh mint leaves'),
    ('Sugar', 0.0, 'SOLID', 'Granulated sugar'),
    ('Soda Water', 0.0, 'LIQUID', 'Carbonated water'),
    ('Horilka', 40.0, 'LIQUID', 'Vodka is a clear distilled alcoholic beverage. Different varieties originated in Ukraine, Poland, Russia, and Sweden.'),
    ('Tomato Juice', 0.0, 'LIQUID', 'Juice extracted from tomatoes'),
    ('Lemon Juice', 0.0, 'LIQUID', 'Juice extracted from fresh lemon'),
    ('Worcestershire Sauce', 0.0, 'LIQUID', 'Fermented liquid condiment'),
    ('Tabasco', 0.0, 'LIQUID', 'Spicy hot sauce'),
    ('Salt', 0.0, 'SOLID', 'Common table salt'),
    ('Pepper', 0.0, 'SOLID', 'Ground pepper'),
    ('Tequila', 40.0, 'LIQUID', 'Tequila is a distilled beverage made from the blue agave plant.'),
    ('Champagne', 12.0, 'LIQUID', 'Champagne is a sparkling wine originated and produced in the Champagne wine region of France.')
ON CONFLICT DO NOTHING;


INSERT INTO cocktail (name, volume, abv, description, recipe)
VALUES
    ('Mojito', 300, 14.0, 'Refreshing rum-based cocktail', 'Muddle mint leaves, lime, and sugar in a glass. Add rum and top with soda water. Serve with ice and garnish with mint.'),
    ('Bloody Mary', 400, 18.0, 'Spicy and savory vodka cocktail', 'Mix vodka, tomato juice, lemon juice, Worcestershire sauce, Tabasco, salt, and pepper in a shaker. Pour into a glass filled with ice and garnish with celery.'),
    ('Death in the Afternoon', 200, 12.07, 'A refreshing champagne cocktail', 'Pour tequila into a glass and top with champagne. Stir gently and garnish with a lime wedge.')
ON CONFLICT DO NOTHING;

-- Associate Mojito with glasses
INSERT INTO cocktail_glass (cocktail_name, glass_name)
SELECT c.name, g.name
FROM cocktail c, glass g
WHERE c.name = 'Mojito'
  AND g.name IN ('Default Glass', 'Highball', 'Hurricane')
ON CONFLICT DO NOTHING;

-- Associate Bloody Mary with glasses
INSERT INTO cocktail_glass (cocktail_name, glass_name)
SELECT c.name, g.name
FROM cocktail c, glass g
WHERE c.name = 'Bloody Mary'
  AND g.name IN ('Default Glass', 'Highball')
ON CONFLICT DO NOTHING;

-- Associate Death in the Afternoon with glasses
INSERT INTO cocktail_glass (cocktail_name, glass_name)
SELECT c.name, g.name
FROM cocktail c, glass g
WHERE c.name = 'Death in the Afternoon'
  AND g.name IN ('Default Glass', 'Martini')
ON CONFLICT DO NOTHING;


INSERT INTO cocktail_ingredient (cocktail_name, ingredient_name, amount)
VALUES
    ((SELECT name FROM cocktail WHERE name = 'Mojito'), (SELECT name FROM ingredient WHERE name = 'Rum'), '50ml'),
    ((SELECT name FROM cocktail WHERE name = 'Mojito'), (SELECT name FROM ingredient WHERE name = 'Lime Juice'), '25ml'),
    ((SELECT name FROM cocktail WHERE name = 'Mojito'), (SELECT name FROM ingredient WHERE name = 'Mint Leaves'), '6 leaves'),
    ((SELECT name FROM cocktail WHERE name = 'Mojito'), (SELECT name FROM ingredient WHERE name = 'Sugar'), '2 tsp'),
    ((SELECT name FROM cocktail WHERE name = 'Mojito'), (SELECT name FROM ingredient WHERE name = 'Soda Water'), 'Top up'),
    ((SELECT name FROM cocktail WHERE name = 'Bloody Mary'), (SELECT name FROM ingredient WHERE name = 'Horilka'), '40ml'),
    ((SELECT name FROM cocktail WHERE name = 'Bloody Mary'), (SELECT name FROM ingredient WHERE name = 'Tomato Juice'), '100ml'),
    ((SELECT name FROM cocktail WHERE name = 'Bloody Mary'), (SELECT name FROM ingredient WHERE name = 'Lemon Juice'), '10ml'),
    ((SELECT name FROM cocktail WHERE name = 'Bloody Mary'), (SELECT name FROM ingredient WHERE name = 'Worcestershire Sauce'), '2 dashes'),
    ((SELECT name FROM cocktail WHERE name = 'Bloody Mary'), (SELECT name FROM ingredient WHERE name = 'Tabasco'), '1 dash'),
    ((SELECT name FROM cocktail WHERE name = 'Bloody Mary'), (SELECT name FROM ingredient WHERE name = 'Salt'), 'Pinch'),
    ((SELECT name FROM cocktail WHERE name = 'Bloody Mary'), (SELECT name FROM ingredient WHERE name = 'Pepper'), 'Pinch'),
    ((SELECT name FROM cocktail WHERE name = 'Death in the Afternoon'), (SELECT name FROM ingredient WHERE name = 'Tequila'), '30ml'),
    ((SELECT name FROM cocktail WHERE name = 'Death in the Afternoon'), (SELECT name FROM ingredient WHERE name = 'Champagne'), 'Top up')
ON CONFLICT DO NOTHING;
