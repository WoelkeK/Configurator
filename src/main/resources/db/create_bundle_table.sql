CREATE TABLE bundle (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        offer_id BIGINT,
                        printer_id BIGINT,
                        total_price DOUBLE,
                        FOREIGN KEY (offer_id) REFERENCES offer(id),
                        FOREIGN KEY (printer_id) REFERENCES printer(id)
);