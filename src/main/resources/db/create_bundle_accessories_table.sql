CREATE TABLE bundle_accessories (
                                    bundle_id BIGINT,
                                    accessory_id BIGINT,
                                    FOREIGN KEY (bundle_id) REFERENCES bundle(id),
                                    FOREIGN KEY (accessory_id) REFERENCES accessory(id)
);