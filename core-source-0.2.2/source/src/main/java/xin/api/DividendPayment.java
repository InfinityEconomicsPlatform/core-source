/******************************************************************************
 * Copyright © 2013-2016 The Nxt Core Developers.                             *
 *                                                                            *
 * See the AUTHORS.txt, DEVELOPER-AGREEMENT.txt and LICENSE.txt files at      *
 * the top-level directory of this distribution for the individual copyright  *
 * holder information and the developer policies on copyright and licensing.  *
 *                                                                            *
 * Unless otherwise agreed in a custom licensing agreement, no part of the    *
 * Nxt software, including this file, may be copied, modified, propagated,    *
 * or distributed except according to the terms contained in the LICENSE.txt  *
 * file.                                                                      *
 *                                                                            *
 * Removal or modification of this copyright notice is prohibited.            *
 *                                                                            *
 ******************************************************************************/

package xin.api;

import xin.Account;
import xin.Asset;
import xin.Attachment;
import xin.XinException;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

public class DividendPayment extends CreateTransaction {

    static final DividendPayment instance = new DividendPayment();

    private DividendPayment() {
        super(new APITag[]{APITag.AE, APITag.CREATE_TRANSACTION}, "asset", "height", "amountTQTPerQNT");
    }

    @Override
    protected JSONStreamAware processRequest(final HttpServletRequest request)
            throws XinException {
        final int height = ParameterParser.getHeight(request);
        final long amountTQTPerQNT = ParameterParser.getAmountTQTPerQNT(request);
        final Account account = ParameterParser.getSenderAccount(request);
        final Asset asset = ParameterParser.getAsset(request);
        if (Asset.getAsset(asset.getId(), height) == null) {
            return JSONResponses.ASSET_NOT_ISSUED_YET;
        }
        final Attachment attachment = new Attachment.ColoredCoinsDividendPayment(asset.getId(), height, amountTQTPerQNT);
        try {
            return this.createTransaction(request, account, attachment);
        } catch (XinException.InsufficientBalanceException e) {
            return JSONResponses.NOT_ENOUGH_FUNDS;
        }
    }

}
