-- Mixed eye colour (colourblend lens): mix colour and its percent, like mixedhaircolor/mixhairpercent
ALTER TABLE avatarlook ADD COLUMN mixedfacecolor int NOT NULL DEFAULT 0;
ALTER TABLE avatarlook ADD COLUMN mixfacepercent int NOT NULL DEFAULT 0;
