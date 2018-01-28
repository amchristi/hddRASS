public static class Manager extends UndoManager implements UndoRedo {

    static final long serialVersionUID = 6721367974521509720L;

    private int indexOfNextAdd;

    private int limit;

    private boolean inProgress;

    private boolean hasBeenDone;

    private boolean alive;

    private final ChangeSupport cs = new ChangeSupport(this);

    public Manager() {
        hasBeenDone = true;
        alive = true;
        inProgress = true;
        indexOfNextAdd = 0;
        limit = 100;
        edits.ensureCapacity(limit);
    }

    @Override
    public void die() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "9b69a652-75cb-4360-bb59-9c67bf174e11");
        int size = edits.size();
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "3edd07fb-d473-4f86-b553-93dd925d1768");
        for (int i = size - 1; i >= 0; i--) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "e8241051-9320-42b8-9a05-8b79162b4d39");
            UndoableEdit e = edits.elementAt(i);
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "54a7d5ba-b203-4817-bd5b-0d9eae3bea17");
            e.die();
        }
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "b271f543-fb24-4052-a810-ab54a67e7255");
        alive = false;
    }

    @Override
    public boolean isInProgress() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "15ceff88-f411-41de-8880-04cece75bd38");
        return inProgress;
    }

    @Override
    public void end() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "09d05068-c44d-4624-8feb-1def8547b71e");
        inProgress = false;
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "23f89afe-dbdd-4d48-898d-153c33fc7d7b");
        this.trimEdits(indexOfNextAdd, edits.size() - 1);
    }

    @Override
    public void undo() throws CannotUndoException {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "425dcc42-60ff-46d5-9898-e101ebfd5e53");
        if (inProgress) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "ced14f88-a494-481d-8d0d-6685d0e78261");
            UndoableEdit edit = editToBeUndone();
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "673fcb39-6390-40a9-a75a-8ee45ba4e8fa");
            if (edit == null) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "8c1443ba-7f86-4494-ba3c-16f41d76ae8a");
                throw new CannotUndoException();
            }
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "11a63cfe-897b-46d7-9858-d4b2d6362001");
            undoTo(edit);
        } else {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "18a1a879-654f-495c-8118-7dff339dd0ba");
            if (!canUndo()) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "f19403b9-19fd-4f1e-9905-d4f4fbb75978");
                throw new CannotUndoException();
            }
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "cf3284c2-6004-4290-89b1-1cb08db33cd1");
            int i = edits.size() - 1;
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "a7d7b7b8-bfe1-49d7-9717-55564f6d312c");
            try {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "5469220e-17fb-4119-9b41-31202e96beb1");
                for (; i >= 0; i--) {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "986fa19d-322b-47ee-be45-5c02003f09d2");
                    edits.get(i).undo();
                }
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "558b879a-8b2f-4913-94f6-4277557ba2c0");
                hasBeenDone = false;
            } finally {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "f5850955-4885-4695-bc0e-c11bf669581f");
                if (i != -1) {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "96e42f32-cd3c-4d8b-988f-556ac0573a02");
                    int size = edits.size();
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "27a09055-7d08-4c49-be29-3bea4ea02afe");
                    while (++i < size) {
                        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "574c6917-1dcd-4881-8aeb-b5a36a2b3494");
                        edits.get(i).redo();
                    }
                }
            }
        }
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "3b0fcb7e-c15c-461b-93e1-b238180d041a");
        cs.fireChange();
    }

    @Override
    protected void undoTo(UndoableEdit edit) throws CannotUndoException {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "e3c4e4d0-16bc-47d5-8593-164e1665b3aa");
        int i = indexOfNextAdd;
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "dc9045f3-5936-4449-8639-58796d632607");
        boolean done = false;
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "296059c4-f1bb-4868-9c36-ca916c9b556b");
        try {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "37815600-76c8-4fe9-ac5d-0965dead9dfb");
            while (!done) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "a8cf2f0c-e09c-4a9a-9d46-bdde3e67e7f6");
                UndoableEdit next = edits.get(--i);
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "b2d9917e-641f-4c7a-b469-87dedaccaee2");
                next.undo();
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "cb135318-92dc-4203-9106-5c1e797b36fb");
                done = next == edit;
            }
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "e79032a6-ae59-4270-8cdc-65aaf2b47c9e");
            indexOfNextAdd = i;
        } finally {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "854b2836-e60e-4628-a4fd-ceba65b05f14");
            if (!done) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "ec079f98-f99a-41b7-8c74-ab3cf2831b19");
                i++;
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "79cc72be-4ca3-40af-aea4-2cfa195d5e59");
                for (; i < indexOfNextAdd; i++) {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "abc959fb-cf79-4741-9007-befde6bb6900");
                    edits.get(i).redo();
                }
            }
        }
    }

    @Override
    public boolean canUndo() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "3316957b-a350-4ae9-9ad6-223ded06d332");
        if (inProgress) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "3885fb87-59f0-4348-9a21-811f35a5ad96");
            UndoableEdit edit = editToBeUndone();
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "7b610980-98c6-45d4-a540-dbb7b5591508");
            return edit != null && edit.canUndo();
        } else {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "bb169379-dd93-492b-8657-be3bddf1a783");
            return !isInProgress() && alive && hasBeenDone;
        }
    }

    @Override
    public void redo() throws CannotRedoException {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "d000ce61-7c79-43ba-a9c3-0a37015e8dcf");
        if (inProgress) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "4884cdbd-6375-494a-a95a-3e1ea6926387");
            UndoableEdit edit = editToBeRedone();
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "b380883d-8d5f-4868-a9b7-dfb6e21ecd93");
            if (edit == null) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "116aa119-3116-45df-aeeb-591cc11817ca");
                throw new CannotRedoException();
            }
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "59f73507-849b-42d4-b71b-59583023bca6");
            redoTo(edit);
        } else {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "9b303428-a1a8-4435-8f41-06d68662b5be");
            if (!canRedo()) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "0e33c792-1c8e-47bb-9f8a-98c0af985647");
                throw new CannotRedoException();
            }
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "d920abc1-2b5c-4af1-9140-c5157d256f30");
            int i = 0;
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "0df1e7ae-dedd-47b1-82b1-647f51418d0d");
            int size = edits.size();
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "468e76cd-6de1-40fa-be66-cada6bdaa965");
            try {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "920ef78e-1cf8-4220-9d86-d8d5b8dbe659");
                for (; i < size; i++) {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "bc940294-9d34-470a-91d2-79b29e7a7510");
                    edits.get(i).redo();
                }
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "3e46b67b-482e-4ba6-ac96-502f5b6371a6");
                hasBeenDone = true;
            } finally {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "b2dacfea-a5dc-465e-82bc-d09ce5b92914");
                if (i != size) {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "f343b113-87b2-4e9f-b427-97a9b1b741dd");
                    while (--i >= 0) {
                        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "becde5dd-b379-47df-a8db-6a3a9f30294f");
                        edits.get(i).undo();
                    }
                }
            }
        }
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "fe0bd04d-1cd0-4d0c-9e8b-5e91a5578c20");
        cs.fireChange();
    }

    @Override
    protected void redoTo(UndoableEdit edit) throws CannotRedoException {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "b77a28b1-eb25-4ad2-98b1-7a160da9dcb4");
        int i = indexOfNextAdd;
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "a5a1f839-2e09-4de6-b08c-14622f72c225");
        boolean done = false;
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "c5299485-6fd3-490d-96bc-6c580d02a3c4");
        try {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "9f15b56b-6417-4e89-9a1f-e292ee1b893b");
            while (!done) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "b818cb94-73ac-407a-8932-576787ad7452");
                UndoableEdit next = edits.elementAt(i++);
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "b0278913-bce6-4184-b418-3501e00e513c");
                next.redo();
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "55d06774-b004-4492-9c5f-3bbb34c76859");
                done = next == edit;
            }
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "26b7d192-d54c-415d-a710-e8cf5a88a5e4");
            indexOfNextAdd = i;
        } finally {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "062af52b-d3d8-4920-b24e-7326a91de559");
            if (!done) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "2cf70b0d-6b31-4eca-be3f-4150d4aa9627");
                i -= 2;
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "c2bc60fe-0029-4cc1-a057-b52d0719bf41");
                for (; i >= indexOfNextAdd; i--) {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "f5ac48ec-d8e1-465b-bc4a-db374056a47f");
                    edits.get(i).undo();
                }
            }
        }
    }

    @Override
    public boolean canRedo() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "c78485f5-c6be-4488-bdbe-a222e79b4fa2");
        if (inProgress) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "84e18bb7-7cdd-44ca-9bba-79fb910534ba");
            UndoableEdit edit = editToBeRedone();
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "d28f0392-e4f5-425e-85af-acca473c799e");
            return edit != null && edit.canRedo();
        } else {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "01b4bb95-e2ed-4373-920e-c79332c3ba7f");
            return !isInProgress() && alive && !hasBeenDone;
        }
    }

    @Override
    public void undoOrRedo() throws CannotRedoException, CannotUndoException {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "4a9b9b74-9669-41e6-bc2c-685b93f795c5");
        if (indexOfNextAdd == edits.size()) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "90309437-21b9-48a1-8992-a356b5fdcba2");
            undo();
        } else {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "e50251ea-0cb8-4203-8729-87b3e37a41b6");
            redo();
        }
    }

    @Override
    public boolean canUndoOrRedo() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "01384403-2cea-4514-9970-f7db50968d41");
        if (indexOfNextAdd == edits.size()) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "09eb0d76-6c6e-4109-ad86-010c0cd60a41");
            return canUndo();
        } else {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "ae0bb169-9f3b-474d-bffa-a763a958ea24");
            return canRedo();
        }
    }

    @Override
    public int getLimit() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "6a8fd43a-8826-47fa-bab8-6569c5914b06");
        return limit;
    }

    @Override
    public void setLimit(int l) {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "b9492d88-1192-4c19-800e-c39be0f6b9da");
        if (!inProgress) throw new RuntimeException("Attempt to call UndoManager.setLimit() after UndoManager.end() has been called");
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "117bb5e7-25fd-444b-8c84-89f00c86ca71");
        limit = l;
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "bac0df80-4c9f-44aa-9086-f5fe92061002");
        trimForLimit();
    }

    @Override
    protected void trimForLimit() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "83e1afe3-6a70-4ee1-96a2-8ef2ba3c3778");
        if (limit >= 0) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "9af64310-6298-4756-912f-75eac3257b5c");
            int size = edits.size();
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "15f93ea4-5e08-4b47-853b-01ca50440ab3");
            if (size > limit) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "3dc50903-997e-4626-9ad2-741a7d60eb6f");
                int halfLimit = limit / 2;
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "71005492-b607-4cb4-bf21-41e08baa5fda");
                int keepFrom = indexOfNextAdd - 1 - halfLimit;
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "3b5e26f6-f521-4421-9fe6-b6dbb010b120");
                int keepTo = indexOfNextAdd - 1 + halfLimit;
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "57e85d48-29b2-4c22-a3e2-0930506a6ba6");
                if (keepTo - keepFrom + 1 > limit) {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "1c52d8f6-db47-459c-8bb0-aea6674cccb2");
                    keepFrom++;
                }
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "93846533-6a48-4143-b1e7-d5b51a3e5833");
                if (keepFrom < 0) {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "1d9655cd-f892-4ba3-a300-8b79fb2d9022");
                    keepTo -= keepFrom;
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "aa4ff180-320a-45e7-8f1f-65fdb3d6e54f");
                    keepFrom = 0;
                }
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "8bd3aed3-ee1f-4296-8c30-fe436e991487");
                if (keepTo >= size) {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "d78f3bab-b39d-4ba6-96b9-6891e32a8a69");
                    int delta = size - keepTo - 1;
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "74d8f1ce-d97d-40c3-9647-2b57bae00a53");
                    keepTo += delta;
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "dd07ecd0-18c5-43fd-b3e6-3133a9b82d9b");
                    keepFrom += delta;
                }
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "8ada3f9b-da08-4e0e-8646-1cfaccef179a");
                trimEdits(keepTo + 1, size - 1);
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "7d7cfea9-1d04-46d4-b623-b93e88a24c64");
                trimEdits(0, keepFrom - 1);
            }
        }
    }

    @Override
    protected void trimEdits(int from, int to) {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "cf56f307-97d4-464f-a5b0-6b7fc4cd8981");
        if (from <= to) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "ffa91e54-da13-4178-8ce7-af38da0e3d9b");
            for (int i = to; from <= i; i--) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "67e65677-3be1-44a7-9df4-65438b9e8a4b");
                UndoableEdit e = edits.elementAt(i);
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "6c4678ea-8f11-41fa-a754-594510dcbd11");
                e.die();
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "77f474d8-336a-4128-a04b-a9a344ab6390");
                edits.removeElementAt(i);
            }
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "8011b527-7922-4857-9cf1-c2b3302d79b1");
            if (indexOfNextAdd > to) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "1fe1b7a3-1f8b-4d79-8563-cb48b5d2c2f5");
                indexOfNextAdd -= to - from + 1;
            } else if (indexOfNextAdd >= from) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "cd520e3e-0dd3-4356-bc82-11b77a33cd32");
                indexOfNextAdd = from;
            }
        }
    }

    @Override
    public void discardAllEdits() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "40b72e48-6c00-4da8-aaa8-936deecb0575");
        Enumeration cursor = edits.elements();
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "1581656a-cb1c-4b6c-946b-b35b4faa0c52");
        while (cursor.hasMoreElements()) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "0b7c32f9-c7cd-40a2-9541-67924b477582");
            UndoableEdit e = (UndoableEdit) cursor.nextElement();
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "885507ca-d476-4150-9b6b-7043537d0a3a");
            e.die();
        }
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "11ebb2cb-b0c0-45b9-b344-4e680c6b3c19");
        edits = new Vector<UndoableEdit>();
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "1048d120-e779-44b2-806e-d040fe4284ee");
        indexOfNextAdd = 0;
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "41c01972-47f1-4010-9d1a-2c5b83799ed3");
        cs.fireChange();
    }

    @Override
    protected UndoableEdit lastEdit() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "295c9982-29ff-4f7e-8762-474fd9e48bf7");
        int count = edits.size();
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "8ff1fb79-1663-47ea-9057-ca46780983c9");
        if (count > 0) return edits.elementAt(count - 1); else return null;
    }

    @Override
    protected UndoableEdit editToBeUndone() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "60fc35c3-7a9f-4487-ba40-1851ecb98f26");
        int i = indexOfNextAdd;
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "39baaa2a-dae4-45ed-bc19-72cf086ea151");
        while (i > 0) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "49291ff6-477f-430d-8b26-81a4e07fea47");
            UndoableEdit edit = edits.elementAt(--i);
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "02a5a551-7117-443f-9825-585f7d273fd7");
            if (edit.isSignificant()) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "0dad1429-c797-42f9-91f0-e2054dbef3b5");
                return edit;
            }
        }
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "eb99756a-3f10-4b9c-8b13-4804c8b3e610");
        return null;
    }

    @Override
    protected UndoableEdit editToBeRedone() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "e5ea1b85-9bf5-4bb3-9584-6cd85d829147");
        int count = edits.size();
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "a2a82495-39d0-4c51-bb97-9f9b7056e596");
        int i = indexOfNextAdd;
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "bd0b77ac-4a58-464e-b7d0-f3c873bebd48");
        while (i < count) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "8a5acdb5-7639-43ed-942c-f8fb5ab9970f");
            UndoableEdit edit = edits.elementAt(i++);
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "308e23b2-de04-4edc-82fc-d5297591b660");
            if (edit.isSignificant()) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "93ef8ca3-670f-4637-8bcb-3f88f0ad1346");
                return edit;
            }
        }
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "876f8275-584e-4eb0-9148-bb6ab9a1d923");
        return null;
    }

    /** Consume an undoable edit.
         * Delegates to superclass and notifies listeners.
         * @param ue the edit
         */
    @Override
    public void undoableEditHappened(final UndoableEditEvent ue) {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "36f9b35a-fa8d-478e-bd24-d2e0b9191f60");
        addEdit(ue.getEdit());
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "1dd93503-c202-474a-835f-1f957b82ce22");
        cs.fireChange();
    }

    @Override
    public boolean addEdit(UndoableEdit anEdit) {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "7fef70d2-51ba-41a7-9dde-e33f603ef426");
        boolean retVal;
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "4cf41cdd-e3b3-466c-a60a-d1321b3e92ce");
        trimEdits(indexOfNextAdd, edits.size() - 1);
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "34d2d3b1-ce90-4f6e-955e-aa308750ad67");
        if (!inProgress) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "925f2f62-3ced-43bd-b862-9fbffdefdd6c");
            retVal = false;
        } else {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "c7393121-a305-4b25-b238-29658528a90e");
            UndoableEdit last = lastEdit();
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "a1b3a931-54c5-466b-a059-7165fb9845a6");
            if (last == null) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "1bb053b4-091a-4122-8df5-2b159959be09");
                edits.addElement(anEdit);
            } else if (!last.addEdit(anEdit)) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "936914cc-2f10-47e3-a4a2-3408bdbb5fd7");
                if (anEdit.replaceEdit(last)) {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "8a046521-f1d2-4ce4-b1b6-50dcaa89b0f2");
                    edits.removeElementAt(edits.size() - 1);
                }
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "4eedbda6-1b7a-421e-85a5-7db079e083d5");
                edits.addElement(anEdit);
            }
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "334295a0-35c7-43b6-aec1-cac61bd9ba35");
            retVal = true;
        }
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "1e9fa608-bd8c-4eca-80ab-28c24d33e981");
        indexOfNextAdd = edits.size();
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "4560501f-ebdf-46e8-9710-80d132ab5ab3");
        trimForLimit();
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "94669ff4-299f-4566-bc44-745f801aafeb");
        return retVal;
    }

    @Override
    public boolean replaceEdit(UndoableEdit anEdit) {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "145335e6-67bd-4ac4-8a6b-137fc7b380ab");
        return false;
    }

    @Override
    public boolean isSignificant() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "9a57b2d6-53af-4418-a6e3-7750cf86911b");
        Enumeration cursor = edits.elements();
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "21ccf47c-e12c-43ab-83da-8dac691211a4");
        while (cursor.hasMoreElements()) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "cf947780-3b1b-4726-8969-ffd4185c419c");
            if (((UndoableEdit) cursor.nextElement()).isSignificant()) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "81e3f665-1d2b-4dc5-8f2d-9ca7fd29fdff");
                return true;
            }
        }
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "5e6f0664-2b08-45d4-90d8-5853ed8d2f1a");
        return false;
    }

    @Override
    public String getPresentationName() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "ecb941b6-0c72-40ba-be24-d44a3b714d1d");
        UndoableEdit last = lastEdit();
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "391dbb03-f481-401d-b58d-153418a7c89f");
        if (last != null) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "2b5f4b6b-8513-47e5-8f3e-855e72e8a908");
            return last.getPresentationName();
        } else {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "e860e565-0f93-4460-ae2d-24df82238a3d");
            return "";
        }
    }

    @Override
    public String getUndoPresentationName() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "71cbca5a-9272-490a-8464-28eebce76560");
        if (canUndo()) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "ede6ce56-a4cb-4980-a777-9d1041379cc8");
            if (inProgress) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "1062ddf2-3893-4692-bf12-83aa64fda974");
                if (canUndo()) {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "bb9bb5f4-f88b-483b-b687-d38a419fcaa5");
                    return editToBeUndone().getUndoPresentationName();
                } else {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "de9b852e-df3e-40f0-a63e-49a3438ad43c");
                    return UIManager.getString("AbstractUndoableEdit.undoText");
                }
            } else {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "c43eb957-613e-48f7-97b6-aeae867e9b13");
                UndoableEdit last = lastEdit();
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "eac5fc90-79a3-4f01-b43c-2c31009e97cb");
                if (last != null) {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "ff4f6391-8bd9-47af-bbd0-24eeec3ac461");
                    return last.getUndoPresentationName();
                } else {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "653a9b9f-1d64-47b2-8f29-bd68643c70cd");
                    String name = getPresentationName();
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "20c1745e-0ad5-45c9-8403-8a49dbb32a68");
                    if (!"".equals(name)) {
                        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "6106647b-0924-479a-bb38-5dae31b4e9f0");
                        name = UIManager.getString("AbstractUndoableEdit.undoText") + " " + name;
                    } else {
                        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "0a6b8759-3ac9-453e-9017-e7747ef71ce2");
                        name = UIManager.getString("AbstractUndoableEdit.undoText");
                    }
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "100a5f72-3845-4f90-8b87-9199b4c4ba30");
                    return name;
                }
            }
        } else {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "622683b7-a313-4976-a95a-eabd3e3fd681");
            return "";
        }
    }

    @Override
    public String getRedoPresentationName() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "65590959-a855-4116-98bb-6e0c1c574ff5");
        if (canRedo()) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "be3d34fb-8607-478a-aa75-5deace89f126");
            UndoableEdit last = lastEdit();
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "60438c2d-4bf8-4d70-89ea-4fd5d95a85f3");
            if (last != null) {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "f1700c38-5036-4bae-95d8-c034578b9352");
                if (inProgress) {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "abff08ad-4972-4cb5-99f2-d751930f09d8");
                    if (canRedo()) {
                        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "4d63bec9-70a7-4667-8b4e-746a0eb5d676");
                        return editToBeRedone().getRedoPresentationName();
                    } else {
                        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "c4583b9b-871b-4364-a459-2cb44574e261");
                        return UIManager.getString("AbstractUndoableEdit.redoText");
                    }
                } else {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "a902ff7e-3a2c-4b98-8d35-01952d94e16d");
                    return super.getRedoPresentationName();
                }
            } else {
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "e5ee3db2-2341-4cbb-912f-f1b884e2f13f");
                String name = getPresentationName();
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "78b2d035-ff59-46e1-8f43-83c706c9dee0");
                if (!"".equals(name)) {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "d132877f-d57a-48f7-a4b6-7be179052af6");
                    name = UIManager.getString("AbstractUndoableEdit.redoText") + " " + name;
                } else {
                    writeline("/home/ubuntu/temp/ManagerOriginal.txt", "a4bad976-36aa-42d1-9e1f-4876f5035eb1");
                    name = UIManager.getString("AbstractUndoableEdit.redoText");
                }
                writeline("/home/ubuntu/temp/ManagerOriginal.txt", "59119ce8-ffc8-4ee5-9017-7ce08ab3df45");
                return name;
            }
        } else {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "10f7edbe-3003-4f56-8654-e2f78fc4276d");
            return "";
        }
    }

    @Override
    public String getUndoOrRedoPresentationName() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "b041b2e9-01ae-4c60-9b93-24c0ce96edc1");
        if (indexOfNextAdd == edits.size()) {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "accaba55-c705-41a2-ae2c-70f984adbbfa");
            return getUndoPresentationName();
        } else {
            writeline("/home/ubuntu/temp/ManagerOriginal.txt", "91354425-3987-49f7-9427-34b21301fa78");
            return getRedoPresentationName();
        }
    }

    @Override
    public String toString() {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "81c601a3-d873-46d5-a4bf-97617f152b17");
        return super.toString() + " hasBeenDone: " + hasBeenDone + " alive: " + alive + " inProgress: " + inProgress + " edits: " + edits + " limit: " + limit + " indexOfNextAdd: " + indexOfNextAdd;
    }

    @Override
    public void addChangeListener(ChangeListener l) {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "fa7472fe-a93d-45ec-9ab2-36d2b279f2b5");
        cs.addChangeListener(l);
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        writeline("/home/ubuntu/temp/ManagerOriginal.txt", "0ae0f6cf-4c1e-4854-87f0-7770adc28e66");
        cs.removeChangeListener(l);
    }

    public void writeline(String fullFilePath, String text) {
        try {
            java.io.File file = new File(fullFilePath);
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter output = new BufferedWriter(fileWriter);
            output.append(text);
            output.newLine();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
