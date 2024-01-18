
# Market
## Market - functions
- sell item
- buy item
- delete item
- sorting
- searching
- display market

## DB: 
Stores items on market
- MarketUsers
	+ ID (PK)
	+ UserID (FK)
- Item
	+ ID (PK)
	+ itemID (FK)
- marketOffer
	+ ID (PK)
	+ buyerID 
	+ creation date
	+ itemID
	+ price
	+ description
	+ status
	+ sellerID
## Object:
- UserService
	+ updateBalance(amount)
- OfferRepository
	+ create(user, price, item, desc)
	+ delete(offerID)
	+ getOffer(offerID)
	+ updatePrice(offerID, newPrice)
	+ updateDesc(offerID, newDesc)
	+ updateStatus(offerID, newStatus)
- MarketService
	+ create(user, price, item, desc)
	+ delete(offerID)
	+ updatePrice(offerID, newPrice)
	+ updateDesc(offerID, newDesc)
	+ updateStatus(offerID, newStatus)
	+ getOfferByUser(user)
	+ getOfferByItem(item)
	+ getAllOffers()
	+ buyItem(offerID, buyerID)
	+ changeItemOwner(oldOwner, newOwner, item)

**Information for Architects: We want to communicate with users and inventory**
